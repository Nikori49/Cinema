package tag;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
/**
 * Tag that implements search film functionality.
 *
 * @author Mykyta Ponomarenko
 * @version 1.0
 */
public class SearchFilmTag extends TagSupport {
    public int doStartTag(){
        JspWriter out = pageContext.getOut();
        String language = (String) pageContext.getSession().getAttribute("language");
        if (language==null) {
            language = "en";
        }
        Locale locale = new Locale(language);
        ResourceBundle resourceBundle = ResourceBundle.getBundle("General", locale);
        try {
            out.write("<link rel=\"stylesheet\" href=\"popUp.css\">\n" +
                    " <script>\n" +
                            "        let request;\n" +
                            "\n" +
                            "        function filmSearch() {\n" +
                            "\n" +
                            "            const v = document.getElementById('search').value;\n" +
                            "            const url = \""+pageContext.getServletContext().getContextPath()+"/SearchFilm?string=\" + v;\n" +
                            "\n" +
                            "            if (window.XMLHttpRequest) {\n" +
                            "                request = new XMLHttpRequest();\n" +
                            "            } else if (window.ActiveXObject) {\n" +
                            "                request = new ActiveXObject(\"Microsoft.XMLHTTP\");\n" +
                            "            }\n" +
                            "\n" +
                            "            try {\n" +
                            "                request.onreadystatechange = getSearchResults;\n" +
                            "                request.open(\"GET\", url, true);\n" +
                            "                request.send();\n" +
                            "                if (v === '') {\n" +
                            "                    hidePopup();\n" +
                            "                } else {\n" +
                            "                    showPopup();\n" +
                            "                }\n" +
                            "\n" +
                            "            } catch (e) {\n" +
                            "                alert(\"Unable to connect to server\");\n" +
                            "            }\n" +
                            "        }\n" +
                            "\n" +
                            "        function getSearchResults() {\n" +
                            "            if (request.readyState === 4) {\n" +
                            "                document.getElementById('searchResults').innerHTML = request.responseText;\n" +
                            "            }\n" +
                            "        }\n" +
                            "\n" +
                            "        function showPopup() {\n" +
                            "            var popup = document.getElementById(\"searchResults\");\n" +
                            "            popup.classList.toggle(\"show\", true);\n" +
                            "            popup.classList.toggle(\"hide\", false);\n" +
                            "        }\n" +
                            "\n" +
                            "        function hidePopup() {\n" +
                            "            var popup = document.getElementById(\"searchResults\");\n" +
                            "            popup.classList.toggle(\"show\", false);\n" +
                            "            popup.classList.toggle(\"hide\", true);\n" +
                            "        }\n" +
                            "    </script>"+
                    "<div  class=\"container\">\n" +
                    "    <form role=\"presentation\" name=\"searchForm\">\n" +
                    "        <div class=\"popup\" >\n" +
                    "            <label for=\"search\">"+resourceBundle.getString("label.searchFilm")+"</label>\n" +
                    "            <input name=\"search\" id=\"search\" type=\"text\" oninput=\"filmSearch()\">\n" +
                    "            <span  class=\"popuptext\" id=\"searchResults\"></span>\n" +
                    "        </div>\n" +
                    "    </form>\n" +
                    "</div>");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return SKIP_BODY;
    }
}
