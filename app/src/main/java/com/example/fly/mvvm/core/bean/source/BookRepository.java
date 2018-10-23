package com.example.fly.mvvm.core.bean.source;

import com.example.fly.mvvm.callback.CallBack;
import com.example.fly.mvvm.core.bean.BaseRepository;
import com.example.fly.mvvm.core.bean.pojo.book.BookListVo;
import com.example.fly.mvvm.core.bean.pojo.book.BookTypeVo;
import com.example.fly.mvvm.network.rx.RxSubscriber;
import com.example.fly.mvvm_library.http.rx.RxSchedulers;

/**
 * @author：tqzhang on 18/7/28 13:00
 */
public class BookRepository extends BaseRepository {

    public void loadBookList(String fCatalogId, String lastId, String rn, final CallBack listener) {
        addSubscribe(apiService.getBookList(fCatalogId, lastId, rn)
                .compose(RxSchedulers.io_main())
                .subscribe(new RxSubscriber<BookListVo>() {
                    @Override
                    protected void onNoNetWork() {
                        super.onNoNetWork();
                        listener.onNoNetWork();
                    }

                    @Override
                    public void onSuccess(BookListVo bookListObject) {
                        listener.onNext(bookListObject);
                    }

                    @Override
                    public void onFailure(String msg) {
                        listener.onError(msg);
                    }
                }));
    }

    public void loadBookTypeData(final CallBack<BookTypeVo> listener) {
        addSubscribe(apiService.getBookType()
                .compose(RxSchedulers.<BookTypeVo>io_main())
                .subscribe(new RxSubscriber<BookTypeVo>() {
                    @Override
                    protected void onNoNetWork() {
                        super.onNoNetWork();
                        listener.onNoNetWork();
                    }

                    @Override
                    public void onSuccess(BookTypeVo bookClassObject) {
                        listener.onNext(bookClassObject);
                    }

                    @Override
                    public void onFailure(String msg) {
                        listener.onError(msg);
                    }
                }));
    }
}
