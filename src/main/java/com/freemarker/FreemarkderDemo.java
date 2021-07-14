package com.freemarker;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import javax.xml.transform.Templates;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * First you have to create a freemarker.template.Configuration instance and adjust its settings.
 * A Configuration instance is the central place to store the application level settings of FreeMarker.
 * Also, it deals with the creation and caching of pre-parsed templates (i.e., Template objects).
 *
 * @author <a href="mailto:luojianwei@pinming.cn">LuoJianwei</a>
 * @since 2021/7/14 17:26
 */
public class FreemarkderDemo {

    public static void main(String[] args) throws IOException, TemplateException {

        Configuration cfg = createConfiguration();

        // From now you should use this single configuration instance (i.e., its a singleton).
        // Note however that if a system has multiple independent components that use FreeMarker,
        // then of course they will use their own private Configuration instances.

        // Do not needlessly re-create Configuration instances; it's expensive, among others because you lose the template cache.
        // Configuration instances meant to be application-level singletons.

        // In multi-threaded applications (like Web sites) the settings in the Configuration instance must not be modified anymore after this point.
        // Thus it can be treated as "effectively immutable" object, so you can continue with safe publishing techniques
        // (see JSR 133 and related literature) to make the instance available for other threads.
        // Like, publish the instance through a final or volatile filed, or through a thread-safe IoC container (like the one provided by Spring).
        // Configuration methods that don't deal with modifying settings are thread-safe.

        // Create a data-model
        Map<String, Object> root  = createDataModel();


        // Templates are represented by freemarker.template.Template instances.
        // Typically you obtain a Template instance from the Configuration instance, using its. getTemplate method.
        // If you store the example template in the test.ftlh file of the earlier set directory, then you can do this:
        // Template temp = cfg.getTemplate("test.ftlh");

        // Configuration caches Template instances, so when you call cfg.getTemplate("test.ftlh") next time,
        // it probably won't read and parse the template file again, just returns the same Template instance as for the first time.
        Template temp = cfg.getTemplate("test.ftlh");

        // As you might already know, data-model + template = output. We already have a data-model (root) and a template (temp),
        // so to get the output we have to merge them. This is done by the process method of the template.
        // It takes the data-model root and a Writer as parameters. It writes the produced output to the Writer.
        // For the sake of simplicity here I write to the standard output:

        // Java I/O related notes: Depending on what out is, you may need to ensure that out.close() is called.
        // This is typically needed when out writes into a file that was opened to store the output of the template.
        // In other times, like in typical Web applications, you must not close out.
        // FreeMarker calls out.flush() after a successful template execution (but tis can be disabled in Configuration),
        // so you don't need to worry about that.
        Writer out = new OutputStreamWriter(System.out);
        temp.process(root, out);

    }

    /**
     * In simple cases you can build data-models using java.lang and java.util classes and custom JavaBeans:
     * Use java.lang.String for strings.
     * Use java.lang.Number subclasses for numbers.
     * Use java.lang.Boolean for boolean values.
     * Use java.util.Date and its subclasses for date/time values
     * Use java.util.List or Java arrays for sequences.
     * Use java.util.Map with String keys for hashes.
     * Use your custom bean class for hashes where the items correspond to the bean properties.
     * For example the price property (getPrice()) of product can be get as product.price.
     * (The actions of the beans can be exposed as well; see much later here)
     */
    private static Map<String, Object> createDataModel() {
        // Create the root hash. We use a Map here, but it could be a JavaBean too.
        Map<String, Object> root = new HashMap<>();

        // Put string "user" into the root
        root.put("user", "Big Joe");

        // Create the "latestProduct" hash. We use a JavaBean here, but it could be a Map too.
        Product latest = new Product();
        latest.setUrl("products/greenmouse.html");
        latest.setName("green mouse");
        // and put it into the root
        root.put("latestProduct", latest);

        return root;
    }

    /**
     * CreateAConfigurationInstance
     */
    public static Configuration createConfiguration() throws IOException {

        // Create your Configuration instance, and specify if up to what FreeMarker
        // version (here 2.3.29) do you want to apply the fixes that are not 100%
        // backward-compatible. See the Configuration JavaDoc for details.
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_29);

        // Specify the source where the template files come from. Here I set a
        // plain directory for it, but non-file-system sources are possible too:
        //cfg.setDirectoryForTemplateLoading(new File("/where/you/store/templates"));

        StringTemplateLoader templateLoader = new StringTemplateLoader();
        templateLoader.putTemplate("test.ftlh", "<html>\n" +
                "<head>\n" +
                "  <title>Welcome!</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "  <h1>Welcome ${user}!</h1>\n" +
                "  <p>Our latest product:\n" +
                "  <a href=\"${latestProduct.url}\">${latestProduct.name}</a>!\n" +
                "</body>\n" +
                "</html>");
        cfg.setTemplateLoader(templateLoader);

        // From here we will set the settings recommended for new projects. These
        // aren't the defaults for backward compatibilty.

        // Set the preferred charset template files are stored in. UTF-8 is
        // a good choice in most applications:
        cfg.setDefaultEncoding("UTF-8");

        // Sets how errors will appear.
        // During web page *development* TemplateExceptionHandler.HTML_DEBUG_HANDLER is better.
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        // Don't log exceptions inside FreeMarker that it will thrown at you anyway:
        cfg.setLogTemplateExceptions(false);

        // Wrap unchecked exceptions thrown during template processing into TemplateException-s:
        cfg.setWrapUncheckedExceptions(true);

        // Do not fall back to higher scopes when reading a null loop variable:
        cfg.setFallbackOnNullLoopVariable(false);

        return cfg;
    }
}
