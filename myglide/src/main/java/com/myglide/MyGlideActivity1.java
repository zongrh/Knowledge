package com.myglide;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class MyGlideActivity1 extends AppCompatActivity {
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_glide1);
        imageView = (ImageView) findViewById(R.id.image_view);
    }

    public void loadImage(View view) {
//        String url = "https://gss2.bdstatic.com/-fo3dSag_xI4khGkpoWK1HF6hhy/baike/c0%3Dbaike80%2C5%2C5%2C80%2C26/sign=218ab7317cc6a7efad2ba0749c93c434/aa64034f78f0f73669585eef0055b319eac413bd.jpg";
//        Glide.with(this).load(url).into(imageView);
//----------------------------------------------------------------1、加载图片
//        Glide.with(this).load(url).into(imageView);
//        仅仅就这一行代码，你已经可以做非常非常多的事情了，包括加载网络上的图片、加载手机本地的图片、加载应用资源中的图片等等。
//        调用Glide.with()方法用于创建一个加载图片的实例。with()方法可以接收Context、Activity或者Fragment类型的参数。
// 也就是说我们选择的范围非常广，不管是在Activity还是Fragment中调用with()方法，都可以直接传this。
// 那如果调用的地方既不在Activity中也不在Fragment中呢？也没关系，我们可以获取当前应用程序的ApplicationContext，
// 传入到with()方法当中。注意with()方法中传入的实例会决定Glide加载图片的生命周期，如果传入的是Activity或者Fragment的实例，
// 那么当这个Activity或Fragment被销毁的时候，图片加载也会停止。如果传入的是ApplicationContext，那么只有当应用程序被杀掉的时候，
// 图片加载才会停止。

// 加载本地图片
//        File file = new File(getExternalCacheDir() + "/image.jpg");
//        Glide.with(this).load(file).into(imageView);
// 加载应用资源
//        int resource = R.drawable.image;
//        Glide.with(this).load(resource).into(imageView);

// 加载二进制流
//        byte[] image = getImageBytes();
//        Glide.with(this).load(image).into(imageView);

// 加载Uri对象
//        Uri imageUri = getImageUri();
//        Glide.with(this).load(imageUri).into(imageView);

//----------------------------------------------------------------2、占位图
//        Glide.with(this)
//                .load(url)
//                .placeholder(R.drawable.loading)
//                .into(imageView);
//        placeholder()方法，然后将占位图片的资源id传入到这个方法中即可。
// 另外，这个占位图的用法其实也演示了Glide当中绝大多数API的用法，
// 其实就是在load()和into()方法之间串接任意想添加的功能就可以了。
// 不过如果你现在重新运行一下代码并点击Load Image，
// 很可能是根本看不到占位图效果的。因为Glide有非常强大的缓存机制，
// 我们刚才加载那张必应美图的时候Glide自动就已经将它缓存下来了，
// 下次加载的时候将会直接从缓存中读取，不会再去网络下载了，因而加载的速度非常快，
// 所以占位图可能根本来不及显示。

//        Glide.with(this)
//                .load(url)
//                .placeholder(R.drawable.loading)
//                .diskCacheStrategy(DiskCacheStrategy.NONE)
//                .into(imageView);
//        串接了一个diskCacheStrategy()方法，并传入DiskCacheStrategy.NONE参数，
// 这样就可以禁用掉Glide的缓存功能。
//        Glide.with(this)
//                .load(url)
//                .placeholder(R.drawable.loading)
//                .error(R.drawable.error)
//                .diskCacheStrategy(DiskCacheStrategy.NONE)
//                .into(imageView);
//        这里又串接了一个error()方法就可以指定异常占位图了。

//        http://p1.pstatp.com/large/166200019850062839d3
//        String url = "http://p1.pstatp.com/large/166200019850062839d3";
//        Glide.with(this)
//                .load(url)
//                .asBitmap()//设置为静态图
//                .placeholder(R.drawable.loading)
//                .error(R.drawable.error)
//                .diskCacheStrategy(DiskCacheStrategy.NONE)
//                .into(imageView);
//        可以看到，这里在load()方法的后面加入了一个asBitmap()方法，这个方法的意思就是说这里只允许加载静态图片，不需要Glide去帮我们自动进行图片格式的判断了。
//        由于调用了asBitmap()方法，现在GIF图就无法正常播放了，而是会在界面上显示第一帧的图片。

//        String url = "http://p1.pstatp.com/large/166200019850062839d3";
//        Glide.with(this)
//                .load(url)
//                .asGif()
//                .placeholder(R.drawable.loading)
//                .error(R.drawable.error)
//                .diskCacheStrategy(DiskCacheStrategy.NONE)
//                .into(imageView);
//        那么既然指定了只允许加载动态图片，如果我们传入了一张静态图片的URL地址又会怎么样呢？
//        如果指定了只能加载动态图片，而传入的图片却是一张静图的话，那么结果自然就只有加载失败喽。


//----------------------------------------------------------------3、指定图片大小
        String url = "https://gss2.bdstatic.com/-fo3dSag_xI4khGkpoWK1HF6hhy/baike/c0%3Dbaike80%2C5%2C5%2C80%2C26/sign=218ab7317cc6a7efad2ba0749c93c434/aa64034f78f0f73669585eef0055b319eac413bd.jpg";
        Glide.with(this)
                .load(url)
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .override(100, 100)
                .into(imageView);
//        override()方法指定了一个图片的尺寸，也就是说，Glide现在只会将图片加载成100*100像素的尺寸，
// 而不会管你的ImageView的大小是多少了。

    }
}