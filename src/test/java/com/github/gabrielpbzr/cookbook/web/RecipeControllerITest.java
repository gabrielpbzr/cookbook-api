package com.github.gabrielpbzr.cookbook.web;

import com.github.gabrielpbzr.cookbook.Application;
import com.github.gabrielpbzr.cookbook.IntegrationTestRunner;
import com.github.gabrielpbzr.cookbook.Setup;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 *
 * @author gabriel
 */
@RunWith(IntegrationTestRunner.class)
public class RecipeControllerITest {
    
    @Setup
    public void setup() {        
    }
    
    @Test
    public void should_list_all_recipes(Application app) {
        String baseUrl=app.getBaseURL()+"/api/recipes";
        HttpResponse<JsonNode> httpResponse = Unirest.get(baseUrl).asJson();
        assertEquals(200, httpResponse.getStatus());
        assertNotNull(httpResponse.getBody().getObject().getJSONArray("data"));
    }
    
    @Test
    public void should_list_a_recipe_by_its_id(Application app) {
        String baseUrl=app.getBaseURL()+"/api/recipes/8b9894fd-0399-400e-9e09-6db0a03a7b77";
        HttpResponse<JsonNode> httpResponse = Unirest.get(baseUrl).asJson();
        assertEquals(200, httpResponse.getStatus());
        assertEquals(httpResponse.getBody().getObject().getString("title"), "Recipe 3");
    }
    
    @Test
    public void should_receive_a_not_found_when_trying_to_list_a_recipe_with_an_inexistent_id(Application app) {
        String baseUrl=app.getBaseURL()+"/api/recipes/2bf8140d-0395-400e-9e09-6db0a03a7b77";
        HttpResponse<JsonNode> httpResponse = Unirest.get(baseUrl).asJson();
        assertEquals(404, httpResponse.getStatus());
    }
    
    @Test
    public void should_create_a_new_recipe(Application app) {
        String baseUrl= app.getBaseURL()+"/api/recipes";
        HttpResponse httpResponse = Unirest.post(baseUrl)
                .field("title", "Recipe title")
                .field("content", "Recipe content")
                .asEmpty();
        assertEquals(201, httpResponse.getStatus());
    }
    
    @Test
    public void should_receive_a_bad_request_response_when_creating_a_new_recipe_without_title(Application app) {
        String baseUrl= app.getBaseURL()+"/api/recipes";
        HttpResponse httpResponse = Unirest.post(baseUrl)
                .field("title", "")
                .field("content", "Recipe content")
                .asEmpty();
        assertEquals(400, httpResponse.getStatus());
    }
    
    @Test
    public void should_receive_a_bad_request_response_when_creating_a_new_recipe_without_content(Application app) {
        String baseUrl= app.getBaseURL()+"/api/recipes";
        HttpResponse httpResponse = Unirest.post(baseUrl)
                .field("title", "Recipe title")
                .field("content", "")
                .asEmpty();
        assertEquals(400, httpResponse.getStatus());
    }
    
    @Test
    public void should_update_a_recipe(Application app) {
        String baseUrl= app.getBaseURL()+"/api/recipes/cdcfb40f-7fba-4b70-8558-fb52e2bea8c2";
        HttpResponse httpResponse = Unirest.patch(baseUrl)
                .field("title", "Recipe changed title")
                .field("content", "Recipe content")
                .field("uuid", "cdcfb40f-7fba-4b70-8558-fb52e2bea8c2")
                .asEmpty();
        assertEquals(200, httpResponse.getStatus());
    }
    
    @Test
    public void should_receive_a_not_found_when_trying_to_update_an_inexistent_recipe(Application app) {
        String targetUuid = "adceb40f-7fba-4b70-8558-fb52e2bea8c2";
        String baseUrl= app.getBaseURL()+"/api/recipes/"+ targetUuid;
        HttpResponse httpResponse = Unirest.patch(baseUrl)
                .field("title", "Recipe changed title")
                .field("content", "Recipe content")
                .field("uuid", targetUuid)
                .asEmpty();
        assertEquals(404, httpResponse.getStatus());
    }
    
    @Test
    public void should_delete_a_recipe(Application app) {
        String baseUrl= app.getBaseURL()+"/api/recipes/4db52dc4-77cd-4a2b-9ee9-140467b9da43";
        HttpResponse httpResponse = Unirest.patch(baseUrl)
                .field("title", "Recipe changed title")
                .field("content", "Recipe content")
                .field("uuid", "4db52dc4-77cd-4a2b-9ee9-140467b9da43")
                .asEmpty();
        assertEquals(200, httpResponse.getStatus());
    }
    
    @Test
    public void should_receive_a_not_found_when_trying_to_delete_an_inexistent_recipe(Application app) {
        String targetUuid = "adceb40f-7fba-4b70-8558-fb52e2bea8c2";
        String baseUrl= app.getBaseURL()+"/api/recipes/"+ targetUuid;
        HttpResponse httpResponse = Unirest.patch(baseUrl)
                .field("title", "Recipe changed title")
                .field("content", "Recipe content")
                .field("uuid", targetUuid)
                .asEmpty();
        assertEquals(404, httpResponse.getStatus());
    }
}
