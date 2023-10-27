package org.example.chatbot.api.test;

import cn.hutool.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;


@SpringBootTest
public class ApiTest {

    @Test
    public void query_unanswered_questions() throws IOException {
        CloseableHttpClient httpClient= HttpClientBuilder.create().build();
        HttpGet get=new HttpGet("https://api.zsxq.com/v2/groups/51112814148244/topics?scope=unanswered_questions&count=20");
        get.addHeader("cookie","sensorsdata2015jssdkcross=%7B%22distinct_id%22%3A%2218a354a6d6a9-00037101559d408b6-7f5d547e-1638720-18a354a6d6b9a0%22%2C%22first_id%22%3A%22%22%2C%22props%22%3A%7B%22%24latest_traffic_source_type%22%3A%22%E5%BC%95%E8%8D%90%E6%B5%81%E9%87%8F%22%2C%22%24latest_search_keyword%22%3A%22%E6%9C%AA%E5%8F%96%E5%88%B0%E5%80%BC%22%2C%22%24latest_referrer%22%3A%22https%3A%2F%2Fbugstack.cn%2F%22%7D%2C%22identities%22%3A%22eyIkaWRlbnRpdHlfY29va2llX2lkIjoiMThhMzU0YTZkNmE5LTAwMDM3MTAxNTU5ZDQwOGI2LTdmNWQ1NDdlLTE2Mzg3MjAtMThhMzU0YTZkNmI5YTAifQ%3D%3D%22%2C%22history_login_id%22%3A%7B%22name%22%3A%22%22%2C%22value%22%3A%22%22%7D%2C%22%24device_id%22%3A%2218a354a6d6a9-00037101559d408b6-7f5d547e-1638720-18a354a6d6b9a0%22%7D; abtest_env=product; zsxq_access_token=A76F04BF-3F8F-73E8-9D89-44023C62E024_A9FD820CC73DFA83; zsxqsessionid=1cee97ce9abb49a7378c4e2d69e94868");
        get.addHeader("Content-Type","application/json; charset=UTF-8");
        CloseableHttpResponse response = httpClient.execute(get);
        if(response.getStatusLine().getStatusCode()== HttpStatus.HTTP_OK){
            String ans = EntityUtils.toString(response.getEntity());
            System.out.println(ans);
        }
        else{
            System.out.println(response.getStatusLine().getStatusCode());
        }
    }

    @Test
    public void answer_questions() throws IOException {
        CloseableHttpClient httpClient= HttpClientBuilder.create().build();
        HttpPost post=new HttpPost("https://api.zsxq.com/v2/topics/211225181555821/answer");
        post.addHeader("cookie","sensorsdata2015jssdkcross=%7B%22distinct_id%22%3A%2218a354a6d6a9-00037101559d408b6-7f5d547e-1638720-18a354a6d6b9a0%22%2C%22first_id%22%3A%22%22%2C%22props%22%3A%7B%22%24latest_traffic_source_type%22%3A%22%E5%BC%95%E8%8D%90%E6%B5%81%E9%87%8F%22%2C%22%24latest_search_keyword%22%3A%22%E6%9C%AA%E5%8F%96%E5%88%B0%E5%80%BC%22%2C%22%24latest_referrer%22%3A%22https%3A%2F%2Fbugstack.cn%2F%22%7D%2C%22identities%22%3A%22eyIkaWRlbnRpdHlfY29va2llX2lkIjoiMThhMzU0YTZkNmE5LTAwMDM3MTAxNTU5ZDQwOGI2LTdmNWQ1NDdlLTE2Mzg3MjAtMThhMzU0YTZkNmI5YTAifQ%3D%3D%22%2C%22history_login_id%22%3A%7B%22name%22%3A%22%22%2C%22value%22%3A%22%22%7D%2C%22%24device_id%22%3A%2218a354a6d6a9-00037101559d408b6-7f5d547e-1638720-18a354a6d6b9a0%22%7D; abtest_env=product; zsxq_access_token=A76F04BF-3F8F-73E8-9D89-44023C62E024_A9FD820CC73DFA83; zsxqsessionid=1cee97ce9abb49a7378c4e2d69e94868");
        post.addHeader("Content-Type","application/json; charset=UTF-8");

        String parmJson="{\n" +
                "  \"req_data\": {\n" +
                "    \"text\": \"我也不是很会\\n\",\n" +
                "    \"image_ids\": [],\n" +
                "    \"silenced\": true\n" +
                "  }\n" +
                "}";
        StringEntity stringEntity=new StringEntity(parmJson,ContentType.create("text/json","UTF-8"));
        post.setEntity(stringEntity);
        CloseableHttpResponse response = httpClient.execute(post);
        if(response.getStatusLine().getStatusCode()==HttpStatus.HTTP_OK){
            String ans = EntityUtils.toString(response.getEntity());
            System.out.println(ans);
        }
        else{
            System.out.println(response.getStatusLine().getStatusCode());
        }
    }
}
