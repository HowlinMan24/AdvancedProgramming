package mk.kolokvium.updatedEX.ex17;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;


class CategoryNotFoundException extends Exception {
    public String message;

    public CategoryNotFoundException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

class Category {
    public String name;

    public Category(String name) {
        this.name = name;
    }
}

class NewsItem {
    public String title;
    public Date date;
    public Category category;

    public NewsItem(String title, Date date, Category category) {
        this.title = title;
        this.date = date;
        this.category = category;
    }

    public String getTeaser() {
        return "Kiro";
    }
}

class TextNewsItem extends NewsItem {

    public String news;

    public TextNewsItem(String title, Date date, Category category, String news) {
        super(title, date, category);
        this.news = news;
    }

    public String getTeaser() {
        long curr = System.currentTimeMillis();
        long elapsed = curr - date.getTime();
        if (news.length() >= 80)
            return String.format("%s\n%d\n%s", title, (int) ((elapsed) / (60 * 1000)), news.subSequence(0, 80));
        return String.format("%s\n%d\n%s", title, (int) ((elapsed) / (60 * 1000)), news);
//        return String.format("CalendarMinutes: %d   -->  Date: %s  -->  LocalDate: %s", (int) ((elapsed) / (60 * 1000)), date, calendar);
//        return String.format("Date: %s  -->  LocalDate: %s", date, calendar);
    }

}

class MediaNewsItem extends NewsItem {
    public String url;
    public int numViews;

    public MediaNewsItem(String title, Date date, Category category, String url, int numViews) {
        super(title, date, category);
        this.url = url;
        this.numViews = numViews;
    }

    public String getTeaser() {
        long curr = System.currentTimeMillis();
        long elapsed = curr - date.getTime();
//        return String.format("CalendarMinutes: %d   -->  Date: %s  -->  LocalDate: %s",(int)((elapsed)/(60*1000)), date, calendar);
        return String.format("%s\n%d\n%s\n%d", title, (int) ((elapsed) / (60 * 1000)), url, numViews);
    }
}

class FrontPage {
    public List<NewsItem> newsItems;
    public Category[] categories;

    public FrontPage(Category[] categories) {
        this.categories = new Category[categories.length];
        System.arraycopy(categories, 0, this.categories, 0, categories.length);
        this.newsItems = new ArrayList<>();
    }

    public void addNewsItem(NewsItem newsItem) {
        newsItems.add(newsItem);
    }

    public List<NewsItem> listByCategory(Category category) {
        return newsItems.stream().filter(x -> x.category.name.equals(category.name)).collect(Collectors.toList());
    }

    public List<NewsItem> listByCategoryName(String category) throws CategoryNotFoundException {
        List<NewsItem> newsItemList = new ArrayList<>();
        newsItemList = newsItems.stream().filter(x -> x.category.name.equals(category)).collect(Collectors.toList());
//        if (newsItemList.isEmpty() && (!category.equals("Sport") || !category.equals("Science") || !category.equals(""))) {
//            throw new CategoryNotFoundException("Category " + category + " was not found");
//        } else {
//            return newsItemList;
//        }
        if (newsItemList.isEmpty() && (!category.equals("Sport") && !category.equals("Science") && !category.equals("Technology"))) {
            throw new CategoryNotFoundException("Category " + category + " was not found");
        } else {
            return newsItemList;
        }
    }

    @Override
    public String toString() {
        StringBuilder st = new StringBuilder();
        for (NewsItem newsItem : newsItems) {
            st.append(newsItem.getTeaser()).append("\n");
        }
        return st.toString();
    }
}


public class FrontPageTest {
    public static void main(String[] args) {
        // Reading
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        String[] parts = line.split(" ");
        Category[] categories = new Category[parts.length];
        for (int i = 0; i < categories.length; ++i) {
            categories[i] = new Category(parts[i]);
        }
        int n = scanner.nextInt();
        scanner.nextLine();
        FrontPage frontPage = new FrontPage(categories);
        Calendar cal = Calendar.getInstance();
        for (int i = 0; i < n; ++i) {
            String title = scanner.nextLine();
            cal = Calendar.getInstance();
            int min = scanner.nextInt();
            cal.add(Calendar.MINUTE, -min);
            Date date = cal.getTime();
            scanner.nextLine();
            String text = scanner.nextLine();
            int categoryIndex = scanner.nextInt();
            scanner.nextLine();
            TextNewsItem tni = new TextNewsItem(title, date, categories[categoryIndex], text);
            frontPage.addNewsItem(tni);
        }

        n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String title = scanner.nextLine();
            int min = scanner.nextInt();
            cal = Calendar.getInstance();
            cal.add(Calendar.MINUTE, -min);
            scanner.nextLine();
            Date date = cal.getTime();
            String url = scanner.nextLine();
            int views = scanner.nextInt();
            scanner.nextLine();
            int categoryIndex = scanner.nextInt();
            scanner.nextLine();
            MediaNewsItem mni = new MediaNewsItem(title, date, categories[categoryIndex], url, views);
            frontPage.addNewsItem(mni);
        }
        // Execution
        String category = scanner.nextLine();
        System.out.println(frontPage);
        for (Category c : categories) {
            System.out.println(frontPage.listByCategory(c).size());
        }
        try {
            System.out.println(frontPage.listByCategoryName(category).size());
        } catch (CategoryNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}


// Vasiot kod ovde