package application;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.web.WebView;

public class WebViewContextMenu
{
    static void Create(WebView webView)
    {
        ContextMenu contextMenu = new ContextMenu();
        
        MenuItem option1 = new MenuItem("Option 1");
        option1.setOnAction(e -> System.out.println("Option 1 selected"));
        
        MenuItem option2 = new MenuItem("Option 2");
        option2.setOnAction(e -> System.out.println("Option 2 selected"));
        
        MenuItem option3 = new MenuItem("Option 3");
        option3.setOnAction(e -> System.out.println("Option 3 selected"));

        contextMenu.getItems().addAll(option1, option2, option3);

        webView.setOnMousePressed(e -> {
            if (e.getButton() == MouseButton.SECONDARY) {
                contextMenu.show(webView, e.getScreenX(), e.getScreenY());
            } else {
                contextMenu.hide();
            }
        });
    }
    
    static String getUrl(String text) {
        if (text.indexOf("://")==-1) {
            return "http://" + text ;
        } else {
            return text ;
        }
    }
}