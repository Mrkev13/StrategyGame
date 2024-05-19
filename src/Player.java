

public class Player {

        private int money;
        private int resources;
        private int army;

        public Player() {
            // Nastavení výchozích hodnot
            if (Mapa.currentDifficulty == Mapa.EASY) {
                this.money = 200;
                this.resources = 200;
                this.army = 100;
            } else if (Mapa.currentDifficulty == Mapa.MEDIUM) {
                this.money = 200;
                this.resources = 200;
                this.army = 100;
            } else if (Mapa.currentDifficulty == Mapa.HARD) {
                this.money = 200;
                this.resources = 200;
                this.army = 100;
            }
        }

        //region get set
        public int getMoney() {
            return money;
        }

        public int getResources() {
            return resources;
        }

        public int getArmy() {
            return army;
        }

    public void setMoney(int money) {
        this.money = money;
    }

    public void setResources(int resources) {
        this.resources = resources;
    }

    public void setArmy(int army) {
        this.army = army;
    }
    //endregion
}
