package com.agrisetu.dto;

import java.math.BigDecimal;

public class FarmerProfileDto {
    private String farmName;
    private BigDecimal totalLandAcres;
    private String primaryCrops;

    public FarmerProfileDto() {}

    public FarmerProfileDto(String farmName, BigDecimal totalLandAcres, String primaryCrops) {
        this.farmName = farmName;
        this.totalLandAcres = totalLandAcres;
        this.primaryCrops = primaryCrops;
    }

    public String getFarmName() { return farmName; }
    public void setFarmName(String farmName) { this.farmName = farmName; }

    public BigDecimal getTotalLandAcres() { return totalLandAcres; }
    public void setTotalLandAcres(BigDecimal totalLandAcres) { this.totalLandAcres = totalLandAcres; }

    public String getPrimaryCrops() { return primaryCrops; }
    public void setPrimaryCrops(String primaryCrops) { this.primaryCrops = primaryCrops; }
}
