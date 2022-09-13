package restAssueredUtility;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

public class Listeners extends TestListenerAdapter {
    public ExtentSparkReporter sparkReporter;
    public ExtentReports extentReports;
    public ExtentTest extentTest;

    public void onStart(ITestContext iTestContext)
    {

        String path=System.getProperty("user.dir") +"/Reports/Spark.html";
        sparkReporter= new ExtentSparkReporter(path);
        sparkReporter.config().setDocumentTitle("AutomationReport");
        sparkReporter.config().setReportName("ReportOverall");
        sparkReporter.config().setTheme(Theme.DARK);
        extentReports=new ExtentReports();
        extentReports.attachReporter(sparkReporter);
        extentReports.setSystemInfo("Host Name","localhost");
        extentReports.setSystemInfo("Environmnent","QA");
        extentReports.setSystemInfo("user","Pragati");
    }
    public void  onSuccess(ITestResult iTestResult)
    {
        extentTest=extentReports.createTest(iTestResult.getName());
        extentTest.log(Status.PASS,"Test is pass"+iTestResult.getName());
    }
    public void  onFail(ITestResult iTestResult)
    {
        extentTest=extentReports.createTest(iTestResult.getName());
        extentTest.log(Status.FAIL,"Test is failed"+iTestResult.getName());
    }
    public void  onSkip(ITestResult iTestResult)
    {
        extentTest=extentReports.createTest(iTestResult.getName());
        extentTest.log(Status.SKIP,"Test is Skipped"+iTestResult.getName());
    }
    public void onFinish(ITestContext iTestContext)
    {
        extentReports.flush();

    }
}
