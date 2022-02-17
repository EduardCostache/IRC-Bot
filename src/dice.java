public class dice
{
    /*
    This class is used to format the server message for the rolldice command
     */
    public static String format(String serverMessage)
    {
        serverMessage = serverMessage.substring(serverMessage.indexOf("+r"));
        serverMessage = serverMessage.substring(serverMessage.indexOf("+"));
        serverMessage = serverMessage.substring(serverMessage.indexOf("e") + 2);
        return serverMessage;
    }

    public static int getBet(String format)
    {
        return Integer.parseInt(format);
    }
}
