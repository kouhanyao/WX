package com.example.wx.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class WxHttpClient {

    private static final Logger logger = LoggerFactory.getLogger(WxHttpClient.class);

    /**
     * 向微信发送get请求
     *
     * @return
     * @throws IOException
     */
    public static String get(String url) throws Exception {
        logger.debug("微信  --- URL-------------" + url);
        String responsestr = HttpClientUtils.getInstance().httpGet(url);
        logger.debug("微信响应-----" + responsestr);
        return responsestr;
    }

    /**
     * 通过post请求向微信发送json字符串
     *
     * @param url
     * @param json
     * @return
     * @throws IOException
     */
    public static String postJson(String url, String json) throws Exception {
        logger.debug("微信  --- URL-------------" + url);
        logger.debug("微信  --- JSON-------------" + json);
        String responsestr = HttpClientUtils.getInstance().postJson(url, json);
        logger.debug("微信响应-----" + responsestr);
        return responsestr;
    }

    /**//**
     * 上传文件
     *
     * @return
     *//*
    public static String sendFilePost(String url, InputStream in, String fileName) {
        logger.debug("微信  --- URL-------------" + url);
        HttpPost httpPost = new HttpPost(url);
        String result = null;
        CloseableHttpResponse response = null;
        try {
            MultipartEntity reqEntity = new MultipartEntity();

            InputStreamBody inputStreamBody = new InputStreamBody(in, fileName);

//            HttpEntity  reqEntity = MultipartEntityBuilder.create().setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
//                    .addPart("media", inputStreamBody).setCharset(CharsetUtils.get("utf-8")).build();

            reqEntity.addPart("media", inputStreamBody);// file1为请求后台的File upload;属性
            httpPost.setEntity(reqEntity);
//            httpPost.addHeader("Content-Type", "multipart/form-data");
            response = PoolingHttpManagerUtil.getWxHttpClient().execute(httpPost);
            if (null != response && response.getStatusLine().getStatusCode() == 200) {
                HttpEntity resEntity = response.getEntity();
                if (null != resEntity) {
                    result = EntityUtils.toString(resEntity, HTTP.UTF_8);
                    System.out.println(result);
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public static String connectHttpsByPost(String url, InputStream inputStream, String fileName) throws Exception {
        URL urlObj = new URL(url);
        //连接
        HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();
        String result = null;
        con.setDoInput(true);

        con.setDoOutput(true);

        con.setUseCaches(false); // post方式不能使用缓存

        // 设置请求头信息
        con.setRequestProperty("Connection", "Keep-Alive");
        con.setRequestProperty("Charset", "UTF-8");
        // 设置边界
        String BOUNDARY = "----------" + System.currentTimeMillis();
        con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

        // 请求正文信息
        // 第一部分：
        StringBuilder sb = new StringBuilder();
        sb.append("--"); // 必须多两道线
        sb.append(BOUNDARY);
        sb.append("\r\n");
        sb.append("Content-Disposition: form-data;name=\"media\";filelength=\"" + inputStream.available() + "\";filename=\"" + fileName + "\"\r\n");
        sb.append("Content-Type:application/octet-stream\r\n\r\n");
        byte[] head = sb.toString().getBytes("utf-8");
        // 获得输出流
        OutputStream out = new DataOutputStream(con.getOutputStream());
        // 输出表头
        out.write(head);
        // 文件正文部分
        // 把文件已流文件的方式 推入到url中
        DataInputStream in = new DataInputStream(inputStream);
        int bytes = 0;
        byte[] bufferOut = new byte[1024];
        while ((bytes = in.read(bufferOut)) != -1) {
            out.write(bufferOut, 0, bytes);
        }
        in.close();
        // 结尾部分
        byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线
        out.write(foot);
        out.flush();
        out.close();
        StringBuffer buffer = new StringBuffer();
        BufferedReader reader = null;
        try {
            // 定义BufferedReader输入流来读取URL的响应
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            if (result == null) {
                result = buffer.toString();
            }
        } catch (IOException e) {
            System.out.println("发送POST请求出现异常！" + e);
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return result;
    }

    public static void main(String[] args) throws FileNotFoundException {
        String url = "https://api.weixin.qq.com/cgi-bin/material/add_material?access_token=SwrQeyQV-ikJqkwYaTypmAG7C1-r9tHfSD6W3LslRhH-S9pGuM_A0HX-Bzxobp8z7TsDuzo7UDcNYARFG8Xd4Q&type=image";
        File file = new File("C:\\Users\\yao\\Downloads\\c7d464797e184a60853f9458e1a90542.jpg");
        FileInputStream inputStream = new FileInputStream(file);
        try {
            connectHttpsByPost(url, inputStream, "c7d464797e184a60853f9458e1a90542");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}
