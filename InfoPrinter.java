import java.util.Random;


import java.util.ArrayList;



public class InfoPrinter{
    private ArrayList<String> alertList = new ArrayList<String>();
    private ArrayList<String> harvestList = new ArrayList<String>();
    private int weevilCount = 0;
    private int totalBananasEaten = 0;
    private int weevilsEaten = 0;
    // events
    private boolean badWeather = false;
    private boolean isFrost = false;
    private boolean highMarket = false;
    private boolean lowMarket = false;




    Random random = new Random();
    public void printInfo(TextManagementGame game){
        Bananas playerBananas = game.getPlayerBananas();
        Wood playerWood = game.getPlayerWood();
        Money playerMoney = game.getPlayerMoney();
        Bananimals playerBananimals = game.getPlayerBananimals();
        System.out.println("\nAlerts\n-------------");
        if(game.getRound() == 39){
            System.out.println("Growing season ends in 10 days!");
        }else if(game.getRound() == 44){
            System.out.println("Growing season ends in 5 days!");
        }
        
        harvestAllBananas(game);
        weevilsArrive(game); // calculate if weevils arrive
        if (game.isWeevils()){ // if they are here, calculate count. if its > 0, print count
            if (weevilCount > 0){
                System.out.println("Weevils count : " + 10 * weevilCount);
                System.out.println("They ate " + totalBananasEaten + " bananas!");
            }
        } 
        if(game.getMonkeyManager() && !isBadWeather()){ // sell all bananas only if player has manager and weather is good
            sellAllBananas(game);
        }
        for(int i = 0; i < alertList.size(); i++){
            System.out.println("(!) " + alertList.get(i));
        }
        
        System.out.println("\nHarvest\n-------------");
        for (String H : harvestList){
            System.out.println(H);
        }
        System.out.println("\nFarms and Markets\n-------------");
        printBananaPlots(game);
        printMarkets(game);
        printBananimalFarms(game);
                System.out.println("\nYour Resources\n-------------");
        System.out.println("Money : " + game.getMoneyString(playerMoney.getMoney()));
        System.out.println("Bananas : " + playerBananas.getBananas());
        System.out.println("Wood : " + playerWood.getWood());
        System.out.println("Bananimals : " + playerBananimals.getBananimals());

    }
    public void growAllBananas(TextManagementGame game){
        int growth = 0;
        for (Generator P : game.getGenerators()){
            
            if (P instanceof BananaPlot){
                
                int rand_int = random.nextInt(11);
                BananaPlot bananaPlot = (BananaPlot) P;
                growth = (20 + rand_int + 7*(game.getSprinklers()));
                bananaPlot.growBananas(growth);
                }
            }
       }
    public int harvestAllBananas(TextManagementGame game){
        int bananaYeild = 0;
        int woodYeild = 0;
        int totalBananaYeild = 0;
        int totalWoodYeild = 0;
        int totalHarvestCount = 0;
        int bananasEaten = 0;
        double weevilEffect = 0;
        totalBananasEaten = 0;
        
        for (Generator P : game.getGenerators()){
            if (P instanceof BananaPlot){
                BananaPlot bananaPlot = (BananaPlot) P;
                if (bananaPlot.getGrowth() >= 100){
                    bananaPlot.setGrowth(0);
                    int rand_int1 = random.nextInt(3);
                    bananaYeild = (4 + rand_int1 + 2*(bananaPlot.getMoreBananas()));
                    if(game.isWeevils()){
                        weevilEffect = 1 - (game.getWeevils() * game.getWeevils())/100.0;
                        if(weevilEffect < 0){
                            weevilEffect = 0;
                        }
                    
                        bananasEaten = (int) (bananaYeild- (bananaYeild * weevilEffect));
                        
                        totalBananasEaten += bananasEaten;
                        bananaYeild -= bananasEaten;
                        //System.out.println("eaten : " + totalBananasEaten);
                        //System.out.println("yeild : " + bananaYeild);
                    }
                    woodYeild = 3 + bananaPlot.getTallTrunk();
                    String message = (bananaPlot.getPlotName() + " -> " + bananaYeild + " bananas, " + woodYeild + " wood");
                    harvestList.add(message);
                    totalBananaYeild += bananaYeild;
                    totalWoodYeild += woodYeild;
                    totalHarvestCount ++;   
                }
                }
            }
            if(totalHarvestCount > 1){
                harvestList.add("\nTotal Banana Yeild -> " + totalBananaYeild);
                harvestList.add("Total Wood Yeild -> " + totalWoodYeild);
            }
            Bananas playerBananas = game.getPlayerBananas();
            playerBananas.addBananas(totalBananaYeild);
            Wood playerWood = game.getPlayerWood();
            playerWood.addWood(totalWoodYeild);
            return totalBananasEaten;
    }
   
    public void printBananaPlots(TextManagementGame game) {
        for (Generator P : game.getGenerators()){
            if (P instanceof BananaPlot){
                BananaPlot bananaPlot = (BananaPlot) P;
                System.out.println(bananaPlot.getPlotName() + "\n\tTaller Trunks : " + bananaPlot.getTallTrunk() + "/3" + "\n\tMore Bananas : " + bananaPlot.getMoreBananas() + "/3" + "\n\tGrowth : " + bananaPlot.getGrowth() + "%");
            }
        }

    }
    public void printMarkets(TextManagementGame game) {
        for (Generator M : game.getGenerators()){
            if (M instanceof Market){
                Market market = (Market) M;
                System.out.println(market.getMarketName() + "\n\tSize : " + market.getSize() + "/3 (" + (4*(1 + market.getSize())) + " bananas)" + "\n\tSales Bonus : " + market.getSalesBonus() + "/3 (+" + (20 * market.getSalesBonus()) + "%)");
            }
        }
    }
    public void printBananimalFarms(TextManagementGame game) {
        for (Generator F : game.getGenerators()){
            if (F instanceof BananimalFarm){
                BananimalFarm bananimalFarm = (BananimalFarm) F;
                System.out.println(bananimalFarm.getFarmName() + "\n\tLevel : " + bananimalFarm.getLevel() + "/2 (" + ((1 + bananimalFarm.getLevel())) + " bananimals)");
            }
        }
    }

    public boolean weevilsArrive(TextManagementGame game){
            if(game.getRound() < 24 || game.isWeevils() == true){
                return false;
            }
            int rand_int3 = random.nextInt(10);
            if (game.getRound()-20 > rand_int3){
                alertList.add("Weevils have Arrived!!!");
                game.setIsWeevils(true);
                return true;
            }
            return false;        
    }
    
    public void calculateWeevils(TextManagementGame game){
        if(game.isWeevils() == true){
            weevilCount = game.getWeevils() + game.countGeneratorType(BananaPlot.class);
            int banmals = game.getPlayerBananimals().getQuantity();
            int initialWeevils = weevilCount;
            int initialBanmals = banmals;
            
            if (banmals >= weevilCount){
                banmals -= weevilCount;
                weevilCount = 0;
            }else{
                weevilCount -= banmals;
                banmals= 0;
            }
            alertList.add( 10 * (initialWeevils - weevilCount) + " weevils were eaten by your bananimals");
            alertList.add( initialBanmals - banmals + " bananimals were defeated by the weevils");
            game.setWeevils(weevilCount);
            game.getPlayerBananimals().setBananimals(banmals);
        }
    }
    public void growBananimals(TextManagementGame game){
        int newBananimals = 0;
        for (Generator B : game.getGenerators()){
            if (B instanceof BananimalFarm){
                BananimalFarm bananimalFarm = (BananimalFarm) B;
                newBananimals += (1 + bananimalFarm.getLevel());
            }
        }
        Bananimals playerBananimals = game.getPlayerBananimals();
        playerBananimals.addBananimals(newBananimals);
    }
    public void sellAllBananas(TextManagementGame game){
        Bananas playerBananas = game.getPlayerBananas();
        Money playerMoney = game.getPlayerMoney();
        if(game.getMonkeyManager()){ // sell all bananas if player has monkey manager
            int sellableBananas = 0;
            for (Generator M : game.getGenerators()){
                if (M instanceof Market){
                    Market market = (Market) M;
                    if(market.getSold() == false){
                        sellableBananas += (4*(1 + market.getSize()));
                    }
                }
            }
            int revenue = 0;
            int toSell = sellableBananas;
            if (toSell > playerBananas.getBananas()){
                toSell = playerBananas.getBananas();
            }
            int toSell2 = toSell;
            SellLoop:
            for (Generator M : game.getGenerators()){
                if (M instanceof Market){
                    Market market = (Market) M;
                    if(market.getSold() == false){
                        if (toSell >= 4*(1 + market.getSize())){
                            revenue += (50*(4*(1 + market.getSize())) * (1 + (.2 * market.getSalesBonus())));
                            toSell -= 4*(1 + market.getSize());
                            playerBananas.removeBananas(4*(1 + market.getSize()));
                            market.setSold(true);
                        }
                        else if (toSell < 4*(1 + market.getSize()) && toSell != 0){
                            revenue += (50*toSell) * (1 + (.2 * market.getSalesBonus()));
                            playerBananas.removeBananas(toSell); 
                            toSell = 0;
                            market.setSold(true);
                        }
                        else{
                            break SellLoop;
                        }
                    } 
                }
            }
            if(isHighMarket()){
                revenue *= 1.3;
            } else if(isLowMarket()){
                revenue *= .7;
            }
            revenue = (revenue + 5) / 10 * 10; //round because i dont want pennies
            playerMoney.addMoney(revenue);
            game.addScore(revenue);
            if (revenue > 0){
                addAlertList("Mr. Monkey Manager sold " + toSell2 + " bananas for " + game.getMoneyString(revenue));
            }
        }
    }
    public void runEvents(TextManagementGame game){
        Random random = new Random();
        Money playerMoney = game.getPlayerMoney();
        Wood playerWood = game.getPlayerWood();
        int event = random.nextInt(34);
        //event = 3;
        if (event < 4){
            badWeather = true;
            addAlertList("Bad weather today, markets are closed");
        } else if(event == 4){
            isFrost = true;
            addAlertList("Plots frosted over, trees didn't grow yesterday");
        } else if(event == 5 || event == 6){
            highMarket = true;
            addAlertList("High market value today! enjoy a 30% sales bonus");
        }else if(event == 7 || event == 8){
            lowMarket = true;
            addAlertList("Low market value today. profits down by 30%");
        }else if(event == 9){
            playerMoney.addMoney(300);
            addAlertList("You got a kind donation from a fellow banana enjoyer! Enjoy you $3");   
        }else if(event == 10){
            playerWood.addWood(3);
            addAlertList("You found 3 wood on the side of the road.");
        }else if(event == 11){
            int treeFallen = random.nextInt(game.countGeneratorType(BananaPlot.class)) + 1;


            //addAlertList("Your tree growing in banana plot " + treeFallen + " has fallen down!");
        }
    }
    public void resetEvents(){
        badWeather = false;
        isFrost = false;
        highMarket = false;
        lowMarket = false;
    }
    public void clearAlerts(){
        alertList.clear();
    }
    public void clearHarvest(){
        harvestList.clear();
    }
    public void addAlertList(String alert){
        alertList.add(alert);
    }
    public boolean isBadWeather() {
        return badWeather;
    }
    public boolean isFrost() {
        return isFrost;
    }
    public boolean isHighMarket() {
        return highMarket;
    }
    public boolean isLowMarket() {
        return lowMarket;
    }
    
}