package tags;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

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
                    "<script>\n" +
                    "    let request;\n" +
                    "    function filmSearch() {\n" +
                    "\n" +
                    "        const v = document.searchForm.search.value;\n" +
                    "        const url = \"${pageContext.request.contextPath }/SearchFilm?string=\" + v;\n" +
                    "\n" +
                    "        if(window.XMLHttpRequest){\n" +
                    "            request=new XMLHttpRequest();\n" +
                    "        }\n" +
                    "        else if(window.ActiveXObject){\n" +
                    "            request=new ActiveXObject(\"Microsoft.XMLHTTP\");\n" +
                    "        }\n" +
                    "\n" +
                    "        try\n" +
                    "        {\n" +
                    "            request.onreadystatechange=getSearchResults;\n" +
                    "            request.open(\"GET\",url,true);\n" +
                    "            request.send();\n" +
                    "            showPopup();\n" +
                    "        }\n" +
                    "        catch(e)\n" +
                    "        {\n" +
                    "            alert(\"Unable to connect to server\");\n" +
                    "        }\n" +
                    "    }\n" +
                    "    function getSearchResults() {\n" +
                    "        if(request.readyState===4){\n" +
                    "            document.getElementById('searchResults').innerHTML=request.responseText;\n" +
                    "        }\n" +
                    "    }\n" +
                    "    function showPopup() {\n" +
                    "        var popup = document.getElementById(\"searchResults\");\n" +
                    "        popup.classList.toggle(\"show\");\n" +
                    "    }\n" +
                    "</script>\n" +
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
