package copyleaks.sdk.api.models;

public class SupportedFiles
{
	private String[] textual;

    private String[] ocr;

    public String[] getTextual ()
    {
        return textual;
    }

    public void setTextual (String[] textual)
    {
        this.textual = textual;
    }

    public String[] getOcr ()
    {
        return ocr;
    }

    public void setOcr (String[] ocr)
    {
        this.ocr = ocr;
    }
}
