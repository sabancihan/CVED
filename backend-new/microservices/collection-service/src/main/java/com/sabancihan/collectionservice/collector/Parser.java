package com.sabancihan.collectionservice.collector;

import com.sabancihan.collectionservice.model.Vulnerability;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.module.ModuleDescriptor;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Collections.emptySet;

@Slf4j
@Component

@RequiredArgsConstructor
public class Parser {

    private List<Vulnerability> vulnerabilityFromMatch(List<VulnMapping.CPEMatch> matches, String cve) {

        if (matches.size() == 0) {
            return Collections.singletonList(Vulnerability.builder().
                    id(cve).
                    vendorName("").
                    softwareName("").
                    build());
        }

        Map<String, List<VulnMapping.CPEMatch>> matchListGrouped =
                matches.stream().collect(Collectors.groupingBy(w -> w.cpe23Uri.split(":")[3] + ":" + w.cpe23Uri.split(":")[4]));

        List<Vulnerability> vulnerabilities = new ArrayList<>();






        for (Map.Entry<String, List<VulnMapping.CPEMatch>> entry : matchListGrouped.entrySet()) {


            var entryValue = entry.getValue();

            var match = entryValue.stream().findFirst();

            if (match.isEmpty())
                continue;

            String cpeUri = match.get().getCpe23Uri();


           Set<Vulnerability.AffectedVersions> affectedVersions = getVersions(entryValue);

              vulnerabilities.add(Vulnerability.builder().
                      id(cve).
                      vendorName(cpeUri.split(":")[3]).
                      softwareName(cpeUri.split(":")[4]).
                      affectedVersions(affectedVersions.stream().map(Vulnerability.AffectedVersions::toString).collect(Collectors.toSet())).
                      build());



        }



        return vulnerabilities;




    }

    private  Set<Vulnerability.AffectedVersions> getVersions(List<VulnMapping.CPEMatch> matches) {


        if (matches.size() == 0) {
            return emptySet();
        }

        Set<Vulnerability.AffectedVersions> affectedVersions = new HashSet<>();
        ModuleDescriptor.Version minVersion = ModuleDescriptor.Version.parse(Integer.MAX_VALUE + "");
        ModuleDescriptor.Version maxVersion =  null;


        String pattern = "(?!\\.)(\\d+(\\.\\d+)+)(?:[-.][A-Z]+)?(?![\\d.])$";


        for (VulnMapping.CPEMatch match : matches) {
            String versionStr = match.getCpe23Uri().split(":")[5];

            if (versionStr.matches(pattern)) {
                ModuleDescriptor.Version version = ModuleDescriptor.Version.parse(versionStr);
                if (version.compareTo(minVersion) < 0) {
                    minVersion = version;
                }
            }



            var endVersion = match.versionEndIncluding == null ? match.versionEndExcluding : match.versionEndIncluding;
            var startVersion = match.versionStartIncluding == null ? match.versionStartExcluding : match.versionStartIncluding;

            if (endVersion != null) {
                if (startVersion != null) {
                    affectedVersions.add(new Vulnerability.AffectedVersions(startVersion, endVersion));
                }
                else if (endVersion.matches(pattern)) {
                    maxVersion = ModuleDescriptor.Version.parse(endVersion);
                }



            }


        }
        var minVersionStr = Objects.equals(minVersion.toString(), Integer.MAX_VALUE + "") ? null : minVersion.toString();

        if (affectedVersions.isEmpty())
            affectedVersions.add(new Vulnerability.AffectedVersions(minVersionStr, maxVersion == null ? null :  maxVersion.toString()));

        return affectedVersions;

    }

    public List<Vulnerability> parse(VulnMapping vulnMapping) {

        return Arrays.stream(vulnMapping.CVE_Items).map(
                cveItem -> {
                    Optional<String> cveId = Optional.of(cveItem).map(cveItem1 -> cveItem1.cve).map(cve -> cve.CVE_data_meta).map(cveDataMeta -> cveDataMeta.ID);

                    if (cveId.isEmpty())
                        return null;


                    List<VulnMapping.CPEMatch> cpeList =
                            Optional.of(cveItem)
                                    .map(cveItem1 -> cveItem1.configurations)
                                    .map(configurations -> configurations.nodes)
                                    .map(Arrays::asList)
                                    .map(nodes -> nodes.stream().map(node -> node.cpe_match))
                                    .map(cpeMatchStream -> cpeMatchStream.flatMap(Arrays::stream))
                                    .orElse(Stream.empty())
                                    .toList();

                    return vulnerabilityFromMatch(cpeList, cveId.get());


                }


        ).filter(Objects::nonNull).flatMap(List::stream).collect(Collectors.toList());

    }


}
