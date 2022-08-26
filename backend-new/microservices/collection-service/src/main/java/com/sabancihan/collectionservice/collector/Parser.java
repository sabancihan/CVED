package com.sabancihan.collectionservice.collector;

import com.sabancihan.collectionservice.model.Vulnerability;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

        }



        return vulnerabilities;




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
