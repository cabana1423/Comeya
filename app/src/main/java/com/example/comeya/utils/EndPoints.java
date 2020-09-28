package com.example.comeya.utils;

public class EndPoints {
    public static String URL= "https://caba-proyect-rest.herokuapp.com/";
    public static String SERVICE_LOGIN= URL+"api_v1.0/login";
    //register user
    public static String SERVICE_POST_USER = URL+"api_v1.0/user";
    public static String SERVICE_PUT_USER = URL+"api_v1.0/user?id=";
    public static String SERVICE_GETtoken_USER = URL+"api_v1.0/u_token?id=";
    //register img rest
    public static String SERVICE_UPIMGREST= URL+"api_v1.0/restimg?id=";
    public static String SERVICE_PUT_IMGREST= URL+"api_v1.0/restimg?id=";
    public static String SERVICE_GET_IMGREST= URL+"api_v1.0/restimg?id=";
    //register rest
    public static String SERVICE_LISTREST= URL+"api_v1.0/rest";
    public static String SERV_REST = URL+"api_v1.0/rest?id=";
    public static String SERV_GETMYREST= URL+"api_v1.0/myrest?id_rest=";
    public static String SERV_PUTMYREST= URL+"api_v1.0/rest?id=";
    //MENU
    public static String SERVICE_LISTMENU= URL+"api_v1.0/menu";
    public static String SERV_VIEWMENU= URL+"api_v1.0/menu";
    public static String SERV_CARG_IMGMENU= URL+"api_v1.0/imgmenu?id=";
    public static String SERV_POSTMENU= URL+"api_v1.0/menu?id=";
    public static String SERV_DELETEMENU= URL+"api_v1.0/menu?id=";
    //Pedidos
    public static String SERV_POST_ORDER= URL+"api_v1.0/order?id=";
    public static String SERV_GET_ORDER= URL+"api_v1.0/order?toker=";
    public static String SERV_GET_TOKER_USER= URL+"api_v1.0/order?toker=";
    public static String SERV_PUT_ORDER= URL+"api_v1.0/order?id=";
    public static String SERV_DELETE_ORDER= URL+"api_v1.0/order?toker=";
    public static String SERV_DELETE_UNORDER= URL+"api_v1.0/unOrder?id=";
    //Factura
    public static String SERV_POST_FAC= URL+"api_v1.0/fac?toker=";
    public static String SERV_GET_ADMI_FAC = URL+"api_v1.0/fac?user=";
    public static String SERV_POST_TOKENFB= URL+"api_v1.0/sendFB";
    public static String SERV_GET_ONEFAC = URL+"api_v1.0/facOne?id=";


    /*para mapview*/public static final String MAPVIEW_BUNDLE_KEY="MapViewBundleKey";
}
