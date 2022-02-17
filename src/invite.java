public class invite
{
    /*
    This class is used to format the server message for the invite command
     */
    public static String getNick(String serverMessage)
    {
        serverMessage = serverMessage.substring(serverMessage.indexOf(" ") + 1);
        serverMessage = serverMessage.substring(serverMessage.indexOf(" ") + 1);
        serverMessage = serverMessage.substring(serverMessage.indexOf("+") + 8);
        return serverMessage;
    }

    public static String getError(String serverMessage, String channel)
    {
        String messageNew;
        serverMessage = serverMessage.substring(serverMessage.indexOf(" ") + 1);
        serverMessage = serverMessage.substring(serverMessage.indexOf(" ") + 1);
        serverMessage = serverMessage.substring(serverMessage.indexOf(" ") + 1);
        messageNew = serverMessage.replace("#"+channel+" :", "");
        return messageNew;
    }
}
