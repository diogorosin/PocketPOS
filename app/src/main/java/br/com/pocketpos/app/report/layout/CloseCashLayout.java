package br.com.pocketpos.app.report.layout;

import java.util.List;

public abstract class CloseCashLayout implements Layout {


    private String reportName;

    private List<CloseCashSummaryLayout> summary;


    public String getReportName() {

        return reportName;

    }

    public void setReportName(String reportName) {

        this.reportName = reportName;

    }

    public List<CloseCashSummaryLayout> getSummary() {

        return summary;

    }

    public void setSummary(List<CloseCashSummaryLayout> summary) {

        this.summary = summary;

    }


}