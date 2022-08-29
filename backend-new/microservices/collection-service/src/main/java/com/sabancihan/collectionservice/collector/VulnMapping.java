package com.sabancihan.collectionservice.collector;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VulnMapping {

    public CVE_ITEM[] CVE_Items;


    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CVE_ITEM {
        public CVE cve;
        public CVEConfig configurations;
    }


    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CVE {
        public CVE_META CVE_data_meta;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CVE_META {
        public String ID;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CVEConfig {
        public CVENode[] nodes;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CVENode {
        public CPEMatch[] cpe_match;
    }


    @JsonIgnoreProperties(ignoreUnknown = true)
    @Data
    public static class CPEMatch {
        public Boolean vulnerable;
        public String cpe23Uri;
        public String versionEndExcluding;
        public String versionEndIncluding;
        public String versionStartIncluding;
        public String versionStartExcluding;
    }


}



