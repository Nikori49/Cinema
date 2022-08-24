function changeLanguage(lang) {
    let request
    const url = "${pageContext.request.contextPath }/ChangeLanguage?lang=" + lang;

    if(window.XMLHttpRequest){
        request=new XMLHttpRequest();
    }
    else if(window.ActiveXObject){
        request=new ActiveXObject("Microsoft.XMLHTTP");
    }

    try
    {
        request.open("GET",url,false);
        request.send();
    }
    catch(e)
    {
        alert("Unable to connect to server");
    }
    document.location.reload();
}