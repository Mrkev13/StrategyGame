

public class Player {

        private int money;
        private int army;
        private int stone;
        private int gold;
        private int wood;

        public Player() {
            // Nastavení výchozích hodnot
            if (Mapa.currentDifficulty == Mapa.EASY) {
                this.money = 1000;
                this.army = 200;
                this.stone = 0;
                this.gold = 0;
                this.wood = 0;
            } else if (Mapa.currentDifficulty == Mapa.MEDIUM) {
                this.money = 1000;
                this.army = 200;
                this.stone = 0;
                this.gold = 0;
                this.wood = 0;
            } else if (Mapa.currentDifficulty == Mapa.HARD) {
                this.money = 1000;
                this.army = 200;
                this.stone = 0;
                this.gold = 0;
                this.wood = 0;
            }
        }

        //region get set
        public int getMoney() {
            return money;
        }


        public int getArmy() {
            return army;
        }


        public int getWood() {
        return wood;
    }


        public int getGold() {
        return gold;
    }


        public int getStone() {
        return stone;
    }


    public void setMoney(int money) {
        this.money = money;
    }


    public void setArmy(int army) {
        this.army = army;
    }


    public void setStone(int stone) {
        this.stone = stone;
    }


    public void setGold(int gold) {
        this.gold = gold;
    }


    public void setWood(int wood) {
        this.wood = wood;
    }
    //endregion
}
