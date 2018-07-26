package com.iamtechknow.d2sbackend;

/**
 * Contains attribute information that may be written into a Diablo II save file.
 */
public class D2CharacterAttributes {
    // Classes that correspond to their save code
    private static final int AMAZON = 0, SORCERESS = 1, NECRO = 2, PALADIN = 3, BARB = 4, DRUID = 5, ASSASSIN = 6;

    private final int str;
    private final int dex;
    private final int vit;
    private final int nrg;
    private final double life;
    private final double stamina;
    private final double mana;

    /**
     * Determine and write the attributes themselves based on the class and level.
     */
    public D2CharacterAttributes(D2Save save) {
        int[] vals = getDefaultAttributes(save);
        str = vals[0]; dex = vals[1]; vit = vals[2]; nrg = vals[3];

        life = calcLife(save, vals[4]);
        stamina = calcStamina(save, vals[5]);
        mana = calcMana(save, vals[6]);
    }

    public int getStr() {
        return str;
    }

    public int getDex() {
        return dex;
    }

    public int getVit() {
        return vit;
    }

    public int getNrg() {
        return nrg;
    }

    /**
     * Returns a fixed-point binary number of the character's life, with a 24-bit integer and a 8-bit fractional value.
     * Here the fractional part is always 0.
     * @return 32-bit representation of life
     */
    public int getLife() {
        int life_whole = (int) life;
        return life_whole << 8;
    }

    /**
     * Returns a fixed-point binary number of the character's stamina
     * @return 32-bit representation of stamina
     */
    public int getStamina() {
        int stamina_whole = (int) stamina, stamina_frac = (int) (256 * (stamina - stamina_whole));
        stamina_whole <<= 8;

        return stamina_whole | stamina_frac;
    }

    /**
     * Returns a fixed-point binary number of the character's mana
     * @return 32-bit representation of mana
     */
    public int getMana() {
        int mana_whole = (int) mana, mana_frac = (int) (256 * (mana - mana_whole));
        mana_whole <<= 8;

        return mana_whole | mana_frac;
    }

    /**
     * Get the default character attributes from level 1
     */
    private int[] getDefaultAttributes(D2Save save) {
        int[] arr;
        switch(save.getClassNum()) {
            case AMAZON:
                arr = new int[]{20, 25, 20, 15, 50, 84, 15};
                break;
            case SORCERESS:
                arr = new int[]{10, 25, 10, 35, 40, 74, 35};
                break;
            case NECRO:
                arr = new int[]{15, 25, 15, 25, 45, 79, 25};
                break;
            case PALADIN:
                arr = new int[]{25, 20, 25, 15, 55, 89, 15};
                break;
            case BARB:
                arr = new int[]{30, 20, 25, 10, 55, 92, 10};
                break;
            case DRUID:
                arr = new int[]{15, 20, 25, 20, 55, 84, 20};
                break;
            default: // Assassin
                arr = new int[]{20, 20, 20, 25, 50, 95, 25};
        }
        return arr;
    }

    private double calcLife(D2Save save, double life) {
        switch(save.getClassNum()) {
            case SORCERESS: // +2 life per level
            case DRUID:
            case NECRO:
                return life + 2 * (save.getLevel() - 1);
            case BARB: // +4 life
                return life + 4 * (save.getLevel() - 1);
            default: // +3 life for Pal, Sin, Amazon
                return life + 3 * (save.getLevel() - 1);
        }
    }

    private double calcStamina(D2Save save, double stamina) {
        switch(save.getClassNum()) {
            case ASSASSIN:
                return stamina + 1.25 * (save.getLevel() - 1);
            default:
                return stamina + (save.getLevel() - 1);
        }
    }

    private double calcMana(D2Save save, double mana) {
        switch(save.getClassNum()) {
            case BARB:
                return mana + (save.getLevel() - 1);
            case PALADIN:
            case AMAZON:
                return mana + 1.5 * (save.getLevel() - 1);
            case ASSASSIN:
                return mana + 1.75 * (save.getLevel() - 1);
            default: // +2 mana for Sorc, Necro, Druid
                return mana + 2 * (save.getLevel() - 1);
        }
    }
}