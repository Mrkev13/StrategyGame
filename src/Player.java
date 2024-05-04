

public class Player {

        private int money;
        private int resources;
        private int army;

        public Player() {
            // Nastavení výchozích hodnot
            if (Mapa.currentDifficulty == Mapa.EASY) {
                this.money = 400;
                this.resources = 400;
                this.army = 200;
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

        public int getMoney() {
            return money;
        }

        public int getResources() {
            return resources;
        }

        public int getArmy() {
            return army;
        }

}
