package libSource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by JacKeTUs on 18.05.2017.
 */
public class Archive {
    private List<ArchiveRecord> archiveRecords;

    public Archive() {
        archiveRecords = new ArrayList<ArchiveRecord>();
    }

    public List<ArchiveRecord> getArchiveRecords() {
        return archiveRecords;
    }

    public void setArchiveRecords(List<ArchiveRecord> archiveRecords) {
        this.archiveRecords = archiveRecords;
    }

    public Archive(List<ArchiveRecord> archiveRecords) {
        this.archiveRecords = archiveRecords;
    }

    public void addArchiveRecord(ArchiveRecord ar) {
        archiveRecords.add(ar);
    }
}
