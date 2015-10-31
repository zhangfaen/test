import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.w3c.css.sac.CSSException;
import org.w3c.css.sac.CSSParseException;
import org.w3c.css.sac.ErrorHandler;

import com.gargoylesoftware.htmlunit.IncorrectnessListener;
import com.gargoylesoftware.htmlunit.InteractivePage;
import com.gargoylesoftware.htmlunit.ScriptException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HTMLParserListener;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlImage;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.javascript.JavaScriptErrorListener;

public class Snippet {
    public static final String VerifyImgPath = "/Users/zhangfaen/Documents/haha.jpg";
    public static String getVerifyCode(String filename) {
        try {
            // 换行符
            final String newLine = "\r\n";
            final String boundaryPrefix = "--";
            // 定义数据分隔线
            String BOUNDARY = "----WebKitFormBoundaryKyy2rsC4lOW6tnIw";
            // 服务器的域名
            URL url = new URL("http://bbb4.hyslt.com/api.php?mod=php&act=upload");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // 设置为POST情
            conn.setRequestMethod("POST");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            // 设置请求头参数
            conn.setRequestProperty("Host", "bbb4.hyslt.com");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("Charsert", "UTF-8");
            conn.setRequestProperty(
                    "User-Agent",
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.155 Safari/537.36");
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
            OutputStream out = new DataOutputStream(conn.getOutputStream());
            StringBuilder sb = new StringBuilder();
            sb.append(boundaryPrefix);
            sb.append(BOUNDARY);
            sb.append(newLine);
            sb.append("Content-Disposition: form-data;name=\"user_name\"" + newLine);
            sb.append(newLine);
            sb.append("18join" + newLine);

            sb.append(boundaryPrefix);
            sb.append(BOUNDARY);
            sb.append(newLine);
            sb.append("Content-Disposition: form-data;name=\"user_pw\"" + newLine);
            sb.append(newLine);
            sb.append("13572468" + newLine);

            sb.append(boundaryPrefix);
            sb.append(BOUNDARY);
            sb.append(newLine);
            sb.append("Content-Disposition: form-data;name=\"yzm_minlen\"" + newLine);
            sb.append(newLine);
            sb.append("5" + newLine);

            sb.append(boundaryPrefix);
            sb.append(BOUNDARY);
            sb.append(newLine);
            sb.append("Content-Disposition: form-data;name=\"yzm_maxlen\"" + newLine);
            sb.append(newLine);
            sb.append("5" + newLine);

            sb.append(boundaryPrefix);
            sb.append(BOUNDARY);
            sb.append(newLine);
            sb.append("Content-Disposition: form-data;name=\"yzmtype_mark\"" + newLine);
            sb.append(newLine);
            sb.append("25" + newLine);

            sb.append(boundaryPrefix);
            sb.append(BOUNDARY);
            sb.append(newLine);
            sb.append("Content-Disposition: form-data;name=\"zztool_token\"" + newLine);
            sb.append(newLine);
            sb.append("" + newLine);

            sb.append(boundaryPrefix);
            sb.append(BOUNDARY);
            sb.append(newLine);
            sb.append("Content-Disposition: form-data;name=\"upload\"; filename=\"1.jpg\""
                    + newLine);
            sb.append("Content-Type: image/jpeg" + newLine);
            sb.append(newLine);
            System.out.println("Request body:\n" + sb.toString() + "\n");
            // 将参数头的数据写入到输出流中
            out.write(sb.toString().getBytes());

            // // 通过url读取womai的验证码图片信息，new一个URL对象
            // URL imgurl = new URL(imageUrl);
            // // 打开链接
            // HttpURLConnection imgconn = (HttpURLConnection) imgurl
            // .openConnection();
            // // 设置请求方式为"GET"
            // imgconn.setRequestMethod("GET");
            // // 超时响应时间为5秒
            // imgconn.setConnectTimeout(5 * 1000);
            // // 通过输入流获取图片数据
            // InputStream inStream = imgconn.getInputStream();
            // DataInputStream in = new DataInputStream(inStream);

            File file = new File(filename);
            // 数据输入流,用于读取文件数据
            DataInputStream in = new DataInputStream(new FileInputStream(file));
            byte[] bufferOut = new byte[1024];
            int bytes = 0;
            while ((bytes = in.read(bufferOut)) != -1) {
                out.write(bufferOut, 0, bytes);
            }
            out.write(newLine.getBytes());
            in.close();
            // 定义最后数据分隔线，即--加上BOUNDARY再加上--。
            byte[] end_data = (newLine + boundaryPrefix + BOUNDARY + boundaryPrefix + newLine)
                    .getBytes();
            // 写上结尾标识
            out.write(end_data);
            out.flush();
            out.close();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                // String jsonStr =
                // "{\"result\":true,\"data\":{\"id\":6663538441,\"val\":\"GNWF8\"}}";
                System.out.println(line);
                String seperator = "\"val\":\"";
                int start = line.indexOf(seperator);
                if (start > 0) {
                    return line.substring(start + seperator.length(), start + seperator.length()
                            + 5);
                }
            }
            return null;
        } catch (Exception e) {
            System.out.println("发送POST请求出现异常！" + e);
            e.printStackTrace();
        }
        return null;
    }

    public static void login() throws Exception {
        WebClient webClient = new WebClient();
        webClient.setIncorrectnessListener(new IncorrectnessListener() {
            @Override
            public void notify(String arg0, Object arg1) {
            }
        });
        webClient.setCssErrorHandler(new ErrorHandler() {
            @Override
            public void warning(CSSParseException exception) throws CSSException {
            }

            @Override
            public void fatalError(CSSParseException exception) throws CSSException {
            }

            @Override
            public void error(CSSParseException exception) throws CSSException {
            }
        });
        webClient.setJavaScriptErrorListener(new JavaScriptErrorListener() {
            @Override
            public void loadScriptError(InteractivePage arg0, java.net.URL arg1, Exception arg2) {
            }

            @Override
            public void malformedScriptURL(InteractivePage arg0, String arg1,
                    MalformedURLException arg2) {
            }

            @Override
            public void scriptException(InteractivePage arg0, ScriptException arg1) {
            }

            @Override
            public void timeoutError(InteractivePage arg0, long arg1, long arg2) {
            }
        });
        webClient.setHTMLParserListener(new HTMLParserListener() {
            @Override
            public void error(String arg0, java.net.URL arg1, String arg2, int arg3, int arg4,
                    String arg5) {
            }

            @Override
            public void warning(String arg0, java.net.URL arg1, String arg2, int arg3, int arg4,
                    String arg5) {
            }
        });

        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setPrintContentOnFailingStatusCode(false);
        HtmlPage loginPage = webClient.getPage("http://passport.womaiapp.com/login");
        HtmlImage hi = (HtmlImage) loginPage.getElementById("captchaImg");
        hi.saveAs(new File(VerifyImgPath));

        HtmlForm hf = (HtmlForm) loginPage.getElementById("fm1");
        hf.getInputByName("username").setValueAttribute("991761");
        hf.getInputByName("password").setValueAttribute("huan220&&&");
        String code = getVerifyCode(VerifyImgPath);
        System.out.println("verify code: " + code);
        hf.getInputByName("captcha").setValueAttribute(code);
        // click to login
        loginPage.getElementById("doLogin").click();
        HtmlPage userPage = webClient.getPage("http://i.womaiapp.com/index.action");
        System.out.println(userPage.getBody().asText());

        // http://procurement.i.womaiapp.com/pro/procurementOrderList.action
        // 采购订单查询
        HtmlPage ordersPage = webClient
                .getPage("http://procurement.i.womaiapp.com/pro/procurementOrderList.action");
        System.out.println(ordersPage.getBody().asText());
        webClient.close();
    }

    public static void main(String[] args) throws Exception {
        login();
    }
}