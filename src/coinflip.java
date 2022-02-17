public class coinflip
{
    /*
    This class is used to format the server message for the coinflip command
     */
    public static String format(String serverMessage)
    {
        serverMessage = serverMessage.substring(serverMessage.indexOf("+c"));
        serverMessage = serverMessage.substring(serverMessage.indexOf("+"));
        serverMessage = serverMessage.substring(serverMessage.indexOf("p") + 2);
        return serverMessage;
    }

    public static int getBet(String format)
    {
        return Integer.parseInt(format.substring(0, format.length()-5));
    }

    public static String getSide(String format)
    {
        return format.substring(format.length()-4);
    }
}
