package com.sabancihan.collectionservice.collector;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.JsonNode;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VulnMapping {

    public CVE_ITEM[] CVE_Items;


    @JsonIgnoreProperties(ignoreUnknown = true)
    class CVE_ITEM {
        public CVE cve;
        public CVEConfig configurations;
    }


    @JsonIgnoreProperties(ignoreUnknown = true)
    class CVE {
        public CVE_META CVE_data_meta;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    class CVE_META {
        public String ID;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    class CVEConfig {
        public CVENode[] nodes;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    class CVENode {
        public CPEMatch[] cpe_match;
    }


    @JsonIgnoreProperties(ignoreUnknown = true)
    class CPEMatch {
        public Boolean vulnerable;
        public String cpe23Uri;
    }


}



