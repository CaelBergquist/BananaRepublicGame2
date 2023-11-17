import java.util.ArrayList;

/**
 * The Generator class represents a generic resource generating item in the game.
 * Generators have a name, a construction cost, and a resource production rate.
 */
abstract class Generator {
    protected String name;
    private Resource constructionCost;
    private int numberConstructed;
    private Resource product;

    /**
     * Creates a new Generator with the given name, construction cost, and resource production rate.
     *
     * @param name                  the name of the Generator
     * @param constructionCost      the cost in resources required to construct the Generator
     * @param resourceProductionRate the rate at which the Generator produces resources per unit of time
     * @param numberConstructed     the number of units of this generator constructed at this time
     * @param product               the type of resource this generator produces
     */
    public Generator(String name) {
        this.name = name;
    }

    /**
     * Gets the name of the Generator.
     *
     * @return the name of the Generator
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the construction cost of the Generator.
     *
     * @return the construction cost of the Generator
     */
    public Resource getConstructionCost() {
        return constructionCost;
    }


    

    /**
     * Gets the number of units constructed of this Generator.
     *
     * @return the number of units constructed of the generator
     */
    public int getNumberConstructed() {
        return numberConstructed;
    }

}