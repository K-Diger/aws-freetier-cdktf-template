package com.mycompany.app;

import com.hashicorp.cdktf.TerraformStack;
import com.hashicorp.cdktf.providers.aws.cloudwatch_log_group.CloudwatchLogGroup;
import com.hashicorp.cdktf.providers.aws.cloudwatch_log_stream.CloudwatchLogStream;
import com.hashicorp.cdktf.providers.aws.db_instance.DbInstance;
import com.hashicorp.cdktf.providers.aws.elastic_beanstalk_application.ElasticBeanstalkApplication;
import com.hashicorp.cdktf.providers.aws.elastic_beanstalk_application_version.ElasticBeanstalkApplicationVersion;
import com.hashicorp.cdktf.providers.aws.elastic_beanstalk_environment.ElasticBeanstalkEnvironment;
import com.hashicorp.cdktf.providers.aws.elastic_beanstalk_environment.ElasticBeanstalkEnvironmentSetting;
import com.hashicorp.cdktf.providers.aws.iam_instance_profile.IamInstanceProfile;
import com.hashicorp.cdktf.providers.aws.iam_role.IamRole;
import com.hashicorp.cdktf.providers.aws.route53_record.Route53Record;
import com.hashicorp.cdktf.providers.aws.s3_bucket.S3Bucket;
import com.hashicorp.cdktf.providers.aws.s3_object.S3Object;
import com.mycompany.app.constant.Configuration;
import com.mycompany.app.construct.aws.iam.profile.EBIamInstanceProfile;
import com.mycompany.app.construct.aws.iam.role.EBIamRole;
import com.mycompany.app.construct.aws.provider.AwsProvider;
import com.mycompany.app.construct.aws.resource.cloud_watch.CWLogGroup;
import com.mycompany.app.construct.aws.resource.eb.EBApp;
import com.mycompany.app.construct.aws.resource.eb.EBAppVer;
import com.mycompany.app.construct.aws.resource.eb.EBEnv;
import com.mycompany.app.construct.aws.resource.eb.EBEnvSetting;
<<<<<<< HEAD:src/main/java/com/mycompany/app/MainStack.java
import com.mycompany.app.construct.aws.resource.route53.Route53RecordDriver;
import com.mycompany.app.construct.aws.resource.s3.SourceBundleS3Bucket;
import com.mycompany.app.construct.aws.resource.s3.SourceBundleS3Object;
=======
import com.mycompany.app.construct.aws.resource.rds.RDSInstance;
import com.mycompany.app.construct.aws.resource.s3.SourceBundleS3Bucket;
import com.mycompany.app.construct.aws.resource.s3.SourceBundleS3Object;
import com.mycompany.app.construct.aws.resource.vpc.AwsDefaultVpc;
import software.constructs.Construct;

>>>>>>> 7e25324b52cec06d1884c6529288e1b10e2101dd:infrastructure/src/main/java/com/mycompany/app/MainStack.java
import java.util.List;
import software.constructs.Construct;

// aws provider argument docs: https://registry.terraform.io/providers/hashicorp/aws/latest/docs#argument-reference
public class MainStack extends TerraformStack {

    public MainStack(final Construct scope, final String id) {
        super(scope, id);

        provisionProvider(this);
        provisionElasticBeanstalk(this);

        CloudwatchLogGroup logGroup = provisionLogGroup(this);
        provisionLogStream(this, logGroup);

<<<<<<< HEAD:src/main/java/com/mycompany/app/MainStack.java
=======
        provisionDefaultVpc(this);

        provisionRDS(this);
>>>>>>> 7e25324b52cec06d1884c6529288e1b10e2101dd:infrastructure/src/main/java/com/mycompany/app/MainStack.java
//        https://blog.hbsmith.io/aws-elastic-beanstalk-amazon-linux2-%ED%99%98%EA%B2%BD%EC%97%90%EC%84%9C%EC%9D%98-%EB%A1%9C%EA%B7%B8-%EC%BB%A4%EC%8A%A4%ED%84%B0%EB%A7%88%EC%9D%B4%EC%A7%95-amazon-cloudwatch-eb-web-console-1dcb9cc79316
//        https://docs.aws.amazon.com/elasticbeanstalk/latest/dg/ebextensions.html
    }

    private void provisionProvider(Construct scope) {
        new AwsProvider(Configuration.Aws.REGION, Configuration.Aws.AWS_ACCESS_KEY,
            Configuration.Aws.AWS_SECRET_KEY)
            .provision(scope);
    }

    private ElasticBeanstalkApplication provisionElasticBeanstalk(Construct scope) {
        ElasticBeanstalkApplication ebApp = new EBApp().provision(scope);

        S3Bucket s3Bucket = new SourceBundleS3Bucket().provision(scope);
        S3Object s3Object = new SourceBundleS3Object(s3Bucket).provision(scope);
        ElasticBeanstalkApplicationVersion ebAppVer =
            new EBAppVer(
                ebApp,
                s3Bucket,
                s3Object
            ).provision(scope);

        IamRole role = new EBIamRole().provision(scope);
        IamInstanceProfile profile = new EBIamInstanceProfile(role).provision(scope);
        List<ElasticBeanstalkEnvironmentSetting> settings =
            new EBEnvSetting(profile)
                .provision(scope);
        ElasticBeanstalkEnvironment ebEnv = new EBEnv(ebApp, ebAppVer, settings).provision(scope);

<<<<<<< HEAD:src/main/java/com/mycompany/app/MainStack.java
        Route53Record route53Record = new Route53RecordDriver(ebEnv.getEndpointUrl()).provision(scope);

=======
>>>>>>> 7e25324b52cec06d1884c6529288e1b10e2101dd:infrastructure/src/main/java/com/mycompany/app/MainStack.java
        return ebApp;
    }

    private CloudwatchLogGroup provisionLogGroup(Construct scope) {
        return new CWLogGroup().provision(scope);
    }

    private CloudwatchLogStream provisionLogStream(Construct scope, CloudwatchLogGroup logGroup) {
        return new CWLogStream(logGroup).provision(scope);
    }
<<<<<<< HEAD:src/main/java/com/mycompany/app/MainStack.java
}
=======

    private void provisionDefaultVpc(Construct scope) {
        new AwsDefaultVpc().provision(scope);
    }

    // preprocess : create default vpc
    private DbInstance provisionRDS(Construct scope) {
        return new RDSInstance().provision(scope);
    }
}
>>>>>>> 7e25324b52cec06d1884c6529288e1b10e2101dd:infrastructure/src/main/java/com/mycompany/app/MainStack.java
