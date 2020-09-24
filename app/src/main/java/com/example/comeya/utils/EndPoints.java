package com.example.comeya.utils;

public class EndPoints {
    public static String URL= "https://caba-proyect-rest.herokuapp.com/";
    public static String SERVICE_LOGIN= URL+"api_v1.0/login";
    public static String SERVICE_LISTMENU= URL+"api_v1.0/menu";
    public static String SERVICE_LISTREST= URL+"api_v1.0/rest";
    //register user
    public static String SERVICE_REGISTER_USER= URL+"api_v1.0/user";
    //register img rest
    public static String SERVICE_UPIMGREST= URL+"api_v1.0/restimg?id=";
    public static String SERVICE_PUT_IMGREST= URL+"api_v1.0/restimg?id=";
    public static String SERVICE_GET_IMGREST= URL+"api_v1.0/restimg?id=";
    //register rest
    public static String SERV_REST = URL+"api_v1.0/rest?id=";
    public static String SERV_GETMYREST= URL+"api_v1.0/myrest?id_rest=";
    public static String SERV_PUTMYREST= URL+"api_v1.0/rest?id=";
    //MENU
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




    /*para mapview*/public static final String MAPVIEW_BUNDLE_KEY="MapViewBundleKey";
}
