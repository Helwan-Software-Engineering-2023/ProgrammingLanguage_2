package Assets;

public class Report extends Common
{
    //=====================( Attributes )=====================
    private String idHead = "rpt";
    private String reportDescription;
    private int numberOfRecordsInReport;
    public static  int numberOfReports = 0;


    //=====================( Methods )=====================

    public Report()
    {
        //set files to read from
        setFilePath("Report.txt");
        setFolderPath(this);

        //start read and format data
        file = new FileHandler(getFolderPath()+getFilePath());
        formatData(file.readFile(),this);

    }


    public Report(String reportDescription,boolean update)
    {
        this();
        this.reportDescription = reportDescription;

        //if updating file do not increase id
        if(!update)
        {
            numberOfReports++;
            setId(idHead+numberOfReports);
            //increase number of record in report
        }
        numberOfRecordsInReport = reportDescription.split("\n").length;

    }


    public void setReportDescription(String reportDescription)
    {
        this.reportDescription =reportDescription;
    }
    public String getReportDescription(){
        return reportDescription;
    }

    public int getNumberOfRecordsInReport() {
        return numberOfRecordsInReport;
    }

    public void setNumberOfRecordsInReport(int numberOfRecordsInReport) {
        this.numberOfRecordsInReport = numberOfRecordsInReport;
    }

    public String toString()
    {
        return "This is Report Class";
    }
}