public class join
{
    /*
    This class is used to format the server message for the join command
     */
    public static String getChannel(String serverMessage)
    {
        serverMessage = serverMessage.substring(serverMessage.indexOf(" ") + 1);
        serverMessage = serverMessage.substring(serverMessage.indexOf(" ") + 1);
        serverMessage = serverMessage.substring(serverMessage.indexOf("+") + 6);
        return serverMessage;
    }
}
