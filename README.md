# Related Categories Service API Project

## Docker
### Build the Docker image

`mvn -P <profile> clean package docker:build`


### Push images to registry
After building the Docker image, you should see it listed when querying Docker under repository "tracksimple/develop"
with a tag associated with it.


```
docker images
REPOSITORY                           TAG                                                           IMAGE ID            CREATED             VIRTUAL SIZE
tracksimple/develop                  related-categories-api-1.0.0-SNAPSHOT-development-mdometita   80374a153ed1        40 seconds ago      852.3 MB
related-categories-api/development   latest                                                        db1e621cdf4a        24 hours ago        852.3 MB
```

You can then push this up to the private repo:

```
docker push tracksimple/develop:related-categories-api-1.0.0-SNAPSHOT-development-mdometita
```

Now run it in docker:

`docker run -d -p 8082:8082 --name relcat-api-xxx 80374a153ed1`

Note: 80374a153ed1 in the example above is the image ID.  You can also supply the <REPOSITORY>:<TAG> parameter instead of the image ID.  e.g.:

`docker run -d -p 8082:8082 --name relcat-api-xxx tracksimple/develop:related-categories-api-1.0.0-SNAPSHOT-development-mdometita`

You can access it via your browser.  When running docker locally: [http://localhost:8082/audience/v1/relatedCategories?partnerId=1739&categoryId=17&countryCode=us&deviceType=desktop]


## Development
### Running locally
Currently, authentication is turned on by default only for production builds.  If you want to enable authentication, simply activate the "withAuthentication" profile in maven during
packaging.  For example:

Some example commands:
```
mvn package exec:java

mvn -Pdevelopment,withAuthentication clean package exec:java

mvn -Psoak,withAuthentication package exec:java
```

### Test URL for Bluekai signing (uses the aa-api-test user)

http://localhost:8082/audience/v1/relatedCategories?partnerId=1739&categoryId=995&countryCode=us&deviceType=desktop&bkuid=3fbb2a37f2abcadb1f2298a464b33dff84bb39b5dfd3332a7314074d9bfac5dd&bksig=e4J%2FPXWAeh9oh%2FEF6TGledsKqdNCXVTKQUK4abjR4vE%3D
