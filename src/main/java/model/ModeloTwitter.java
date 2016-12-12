package model;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;
/**
 * Created by mario on 11-12-16.
 */
public class ModeloTwitter {
    public final static String OAUTH_CONSUMER_KEY = "STZ6pAqJqsiPMiN1acVlg24Xy";
    public final static String OAUTH_CONSUMER_SECRET = "4veMKR2yKe76vyGpT45RUyEdtWYAhqNK25rlDeQog6xRI9dqg0";
    public final static String OAUTH_ACCESS_TOKEN = "3184806281-TLfa8Qw56kzx3yzAYlJt13WCk3vs8jCWWPKpus0";
    public final static String OAUTH_ACCESS_TOKEN_SECRET = "nO3egtA8GPKmfX2iY8Lo3iW7omemKRZayOSBuMjwJ604b";



    public Usuario showUser(String usuario){
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true);
        cb.setOAuthConsumerKey(OAUTH_CONSUMER_KEY);
        cb.setOAuthConsumerSecret(OAUTH_CONSUMER_SECRET);
        cb.setOAuthAccessToken(OAUTH_ACCESS_TOKEN);
        cb.setOAuthAccessTokenSecret(OAUTH_ACCESS_TOKEN_SECRET);
        cb.setJSONStoreEnabled(true);
        try {
            Usuario twittero = new Usuario();
            Twitter twitter = new TwitterFactory(cb.build()).getInstance();
            User user = twitter.showUser(usuario);
            if (user.getStatus() != null) {
                twittero.setName(user.getScreenName());
                twittero.setProfile_image(user.getProfileImageURLHttps());
                twittero.setBackground_image(user.getProfileBackgroundImageUrlHttps());
                twittero.setDescription(user.getDescription());
                twittero.setScreen_name(user.getName());
                if(twittero.getBackground_image() == null){
                    twittero.setBackground_image("https://abs.twimg.com/images/themes/theme1/bg.png");
                }
            } else {
                // the user is protected
                System.out.println("@" + user.getScreenName());
                return null;
            }
            return twittero;
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to delete status: " + te.getMessage());
            return null;
        }
    }
}
