public class russRoulette
{
    /*
    This class is used to format the server message for the russroulette command
     */
    public static String format(String serverMessage)
    {
        serverMessage = serverMessage.substring(serverMessage.indexOf("+r"));
        serverMessage = serverMessage.substring(serverMessage.indexOf("+"));
        serverMessage = serverMessage.substring(serverMessage.indexOf("e") + 5);
        return serverMessage;
    }

    public static int getBet(String format)
    {
        return Integer.parseInt(format);
    }
}
