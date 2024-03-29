package services;

public class HTMLGenerator {
    public static String generateHTML(String title, String appName, String content) {
        return String.format("""
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <title>%s</title>
                <style>
                    body {
                        font-family: Arial, sans-serif;
                        background-color: #ffffff; 
                    }
                    .container {
                        width: 50%%;
                        margin: 50px auto;
                        padding: 20px;
                        border: 2px solid #03667a; 
                        border-radius: 8px;
                    }
                    h2 {
                        text-align: center;
                        background-color: #03737a; 
                        color: #ffffff; 
                        padding: 10px; 
                        border-radius: 5px; 
                    }
                    p {
                        text-align: center;
                    }
                </style>
            </head>
            <body>
                <div class="container">
                    <h2>%s</h2>
                    <p>%s</p>
                </div>
            </body>
            </html>
            """, title, title, content);
    }

    public static void main(String[] args) {
        String title = "Reply Email Form";
        String content = "Please provide an update on the request.";
        String htmlString = generateHTML(title, "", content);
        System.out.println(htmlString);
    }
}
