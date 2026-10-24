package load;

import java.nio.file.Path;
import java.util.List;

public record LoadedList(List<Object> items, List<Path> loadedOrigin) {
}
