# Media Planner

اپلیکیشن Android برای طراحی و تحلیل پلن رسانه‌ای با قابلیت اتصال به Gemini.

## اجرای آنلاین Gemini در اپ

این پروژه برای درخواست‌های Gemini به اینترنت نیاز دارد. Workflow ساخت APK به‌صورت خودکار مجوز `INTERNET` را در پروژه استخراج‌شده بررسی و در صورت نیاز اضافه می‌کند.

### تنظیم کلید Gemini در GitHub Actions

کلید واقعی Gemini را داخل کد یا `project.zip` قرار ندهید.

در GitHub:

**Settings → Secrets and variables → Actions → New repository secret**

نام Secret را دقیقاً این بگذارید:

`GEMINI_API_KEY`

سپس APK را از طریق GitHub Actions بسازید. Workflow کلید را فقط هنگام Build به فایل `.env` می‌دهد.

اگر Secret تنظیم نشده باشد، Build ممکن است موفق شود اما درخواست‌های Gemini در APK کار نخواهند کرد.

## اجرای محلی

**Prerequisites:** Android Studio

1. پروژه را استخراج و در Android Studio باز کنید.
2. فایل `.env` بسازید:

```env
GEMINI_API_KEY=YOUR_GEMINI_API_KEY
```

3. اگر `debugConfig` مربوط به AI Studio در پروژه وجود دارد، خط signing آن را برای Build محلی حذف کنید.
4. دستگاه یا Emulator را به اینترنت وصل کنید.
5. برنامه را اجرا کنید و درخواست Gemini را تست کنید.

## نکته امنیتی

کلید Gemini را در GitHub repository، کد Kotlin، APK یا چت عمومی قرار ندهید. برای نسخه تولیدی، استفاده از Firebase AI Logic همراه با App Check گزینه امن‌تری برای اتصال Gemini از داخل اپ موبایل است. Firebase AI Logic برای Android به‌طور رسمی SDK و لایه proxy ارائه می‌کند و می‌تواند کلید Gemini را خارج از کد اپ نگه دارد.
