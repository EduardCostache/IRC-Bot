public class list
{
    /*
    This class is used to format the server message for the serverHello command
     */
    public static String getChannels(String serverMessage)
    {
        serverMessage = serverMessage.substring(serverMessage.indexOf(" ") + 1);
        serverMessage = serverMessage.substring(serverMessage.indexOf(" ") + 1);
        serverMessage = serverMessage.substring(serverMessage.indexOf(" ") + 1);
        serverMessage = serverMessage.substring(0, serverMessage.indexOf(" "));
        return serverMessage;
    }
}
