public class City {
    private final String name;
    private final String region;
    private final int happinessRank;
    private final double happinessScore;
    private final double whiskerHigh;
    private final double whiskerLow;
    private final double economy;
    private final double family;
    private final double health;
    private final double freedom;
    private final double generosity;
    private final double trust;
    private final double dystopia;

    public City(String[] cityInfo) {
        this.name = cityInfo[0];
        this.region = cityInfo[1];
        this.happinessRank = Integer.parseInt(cityInfo[2]);
        this.happinessScore = Double.parseDouble(cityInfo[3]);
        this.whiskerHigh = Double.parseDouble(cityInfo[4]);
        this.whiskerLow = Double.parseDouble(cityInfo[5]);
        this.economy = Double.parseDouble(cityInfo[6]);
        this.family = Double.parseDouble(cityInfo[7]);
        this.health = Double.parseDouble(cityInfo[8]);
        this.freedom = Double.parseDouble(cityInfo[9]);
        this.generosity = Double.parseDouble(cityInfo[10]);
        this.trust = Double.parseDouble(cityInfo[11]);
        this.dystopia = Double.parseDouble(cityInfo[12]);
    }

    public String getName() {
        return name;
    }

    public String getRegion() {
        return region;
    }

    public int getHappinessRank() {
        return happinessRank;
    }

    public double getHappinessScore() {
        return happinessScore;
    }

    public double getWhiskerHigh() {
        return whiskerHigh;
    }

    public double getWhiskerLow() {
        return whiskerLow;
    }

    public double getEconomy() {
        return economy;
    }

    public double getFamily() {
        return family;
    }

    public double getHealth() {
        return health;
    }

    public double getFreedom() {
        return freedom;
    }

    public double getGenerosity() {
        return generosity;
    }

    public double getTrust() {
        return trust;
    }

    public double getDystopia() {
        return dystopia;
    }
}