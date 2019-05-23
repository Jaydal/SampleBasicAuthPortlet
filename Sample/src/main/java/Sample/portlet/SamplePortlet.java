package Sample.portlet;

import Sample.constants.SamplePortletKeys;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import org.osgi.service.component.annotations.Component;

/**
 * @author ASUS
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + SamplePortletKeys.Sample,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class SamplePortlet extends MVCPortlet {
String url="https://api.registry-staging.apps.gov.ph:8044/registries/City_Profile/records";
	@Override
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		// TODO Auto-generated method stub
		renderRequest.setAttribute("data", MyGETRequest());
	
		super.doView(renderRequest, renderResponse);

	}
	public String MyGETRequest() throws IOException {
	    String readLine = null;
		HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
		String encoded = Base64.getEncoder().encodeToString(("joseph:password").getBytes(StandardCharsets.UTF_8));  //Java 8
		connection.setRequestProperty("Authorization", "Basic "+encoded);
	    int responseCode = connection.getResponseCode();
	    if (responseCode == HttpURLConnection.HTTP_OK) {
	        BufferedReader in = new BufferedReader(
	            new InputStreamReader(connection.getInputStream()));
	        StringBuffer response = new StringBuffer();
	        while ((readLine = in .readLine()) != null) {
	            response.append(readLine);
	        } in .close();
	        // print result
	        System.out.println("JSON String Result " + response.toString());
	        return response.toString();
	        //GetAndPost.POSTRequest(response.toString());
	    } else {
	        System.out.println("GET NOT WORKED");
	        return "";
	    }
	}

}