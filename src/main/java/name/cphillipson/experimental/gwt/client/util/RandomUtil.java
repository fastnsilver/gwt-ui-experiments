package name.cphillipson.experimental.gwt.client.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import com.google.gwt.user.client.Random;


public class RandomUtil {

    private RandomUtil() {}

    private static String[] dummyStrings = new String[] { "504", "Adelaide", "AloneSoFar", "AmITooLate", "BarrierReef",
        "BelAir", "BigBrownEyes", "BirdInACage", "BlindingSheetsofRain", "Bloomington", "BookOfPoems",
        "BorrowedBride", "Broadway", "BuickCityComplex", "BustedAfternoon", "CantGetaLine", "Coahuila",
        "ColorOfALonelyHeartIsBlue", "CrashOnTheBarrelhead", "CurtainCalls", "DanceWithMe", "DesignsOnYou",
        "DesperateTimes", "Doreen", "DressingRoomWalls", "DrowningintheDays", "EarlyMorning", "EyesForYou",
        "Firefly", "FourLeafClover", "FriendsForever", "GoingGoingGone", "HandsOff", "HeresToTheHalcyon",
        "HouseThatUsedToBe", "IWillRemain", "IfMyHeartWasACar", "IntheSatelliteRidesaStar", "Indefinitely",
        "Jagged", "JustLikeCalifornia", "KensPolkaThing", "KingOfAllTheWorld", "LetTheIdiotSpeak", "LonelyHoliday",
        "MakingLoveToYou", "MamaTried", "MeltShow", "MissMolly", "Moonlight", "MurderOrAHeartAttack",
        "MySweetBlueEyedDarlin", "MyTwoFeet", "NervousGuy", "Nineteen", "Niteclub", "NoBabyI", "NoMother",
        "OldFamiliarSteam", "Oppenheimer", "OverTheCliff", "Question", "RayCharles", "Ride", "RollerskateSkinny",
        "Salome", "SheLovesTheSunset", "SingularGirl", "Smokers", "SoundOfRunning", "Stoned",
        "StreetsOfWhereImFrom", "StIgnatius", "TheColorOfALonelyHeartIsBlue", "TheEasyWay", "TheFool", "TheNewKid",
        "TheOne", "TheOtherShoe", "ThisBeautifulThing", "Timebomb", "TupeloCountyJail", "UpTheDevilsPay",
        "Valentine", "ValiumWaltz", "Victoria", "WIFE", "Weightless", "WhatIWouldntDo", "WhatWeTalkAbout",
        "WishTheWorst", "WontBeHome", "WTxTeardrops", "YouBelongToMyHeart" };

    private static final int BYTE_THRESHOLD = 256;
    private static final int CHAR_THRESHOLD = BYTE_THRESHOLD;
    private static final int SHORT_THRESHOLD = 1000;
    private static final int INT_THRESHOLD = SHORT_THRESHOLD;
    private static final int LONG_THRESHOLD = INT_THRESHOLD;
    private static final int DEFAULT_WORD_LENGTH = 15;

    public static String createString() {
        int index = Random.nextInt(dummyStrings.length);
        return dummyStrings[index];
    }

    public static String createString(String[] seed) {
        int index = Random.nextInt(seed.length);
        return seed[index];
    }

    public static byte createByte() {
        return (byte) Random.nextInt(BYTE_THRESHOLD);
    }

    public static short createShort() {
        return (short) Random.nextInt(SHORT_THRESHOLD);
    }

    public static int createInt() {
        return Random.nextInt(INT_THRESHOLD);
    }

    public static int createInt(int range) {
        return Random.nextInt(range);
    }

    public static long createLong() {
        return Random.nextInt(LONG_THRESHOLD);
    }

    public static float createFloat() {
        return (float) createDouble();
    }

    public static double createDouble() {
        return Random.nextInt(100000) / 100.0;
    }

    public static boolean createBoolean() {
        return Random.nextBoolean();
    }

    public static char createChar() {
        return (char) Random.nextInt(CHAR_THRESHOLD);
    }

    public static Date createDate() {
        return new Date();
    }

    public static BigDecimal createBigDecimal() {
        BigDecimal x = new BigDecimal(createDouble());
        return x.setScale(2, RoundingMode.HALF_UP);
    }

    public static Object createEnum(Class enumClass) {
        Object[] enums = enumClass.getEnumConstants();
        return enumClass.getEnumConstants()[Random.nextInt(enums.length)];
    }

    public static String createWord() {
        return createWord(DEFAULT_WORD_LENGTH);
    }

    /**
     * Generates a Random word with the given length consisting of uppercase and lowercase letters and
     * numbers.
     * 
     * @param len
     *            Amount of Random characters to generate
     * @return Random Word containing letters and numbers.
     */
    public static String createWord(int len) {
        return createWord(len, null);
    }

    /**
     * Generates a Random word with the given length.
     * 
     * @param len
     *            Amount of Random characters to generate
     * @param alphabet
     *            Alphabet to generate from.
     * @return Random Word containing letters and numbers.
     */
    public static String createWord(int len, char[] alphabet) {
        if (alphabet == null) {
            alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890".toCharArray();
        }

        StringBuffer out = new StringBuffer(len);
        for (int i = 0; i < len; i++) {
            out.append(alphabet[Random.nextInt(alphabet.length)]);
        }

        return out.toString();
    }
}
