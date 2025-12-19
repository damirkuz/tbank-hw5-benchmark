package academy;

import academy.entity.ClassInfo;
import academy.service.InspectionService;
import academy.service.ObjectCreationService;
import academy.util.Formatter;
import academy.util.FormatterFactory;

public class ClassInspector {
    private static final InspectionService inspectionService = new InspectionService();
    private static final ObjectCreationService creationService = new ObjectCreationService();

    public static String inspect(Class<?> clazz, String format) {
        ClassInfo classInfo = inspectionService.inspectClass(clazz);
        Formatter formatter = FormatterFactory.getFormatter(format);
        return formatter.format(classInfo);
    }

    public static <T> T create(Class<T> clazz) {
        return creationService.create(clazz);
    }
}
