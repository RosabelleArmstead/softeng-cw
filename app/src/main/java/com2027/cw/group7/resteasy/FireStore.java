package com2027.cw.group7.resteasy;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.RuntimeExecutionException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


class LoadAllContinuation<T extends FireStore> implements Continuation<QuerySnapshot, Map<String, T>> {
    private T cls;

    public LoadAllContinuation(T cls) {
        this.cls = cls;
    }

    @Override
    public Map<String, T> then(@NonNull Task<QuerySnapshot> t) throws Exception {
        Map<String, T> map = new HashMap<>();
        try {
            QuerySnapshot qs = t.getResult();
            for (DocumentSnapshot ds : qs.getDocuments()) {
                T temp = cls.create(ds);
                map.put(ds.getId(), temp);
            }
        } catch (Exception ex) {
            Log.d("RESTEASY_Error", ex.toString());
        }
        return map;
    }
}
class LoadContinuation<T extends FireStore> implements Continuation<DocumentSnapshot, T> {
    private T cls;

    public LoadContinuation(T cls) {
        this.cls = cls;
    }

    @Override
    public T then(@NonNull Task<DocumentSnapshot> t) throws Exception {
        try {
            DocumentSnapshot ds = t.getResult();
            if (ds.exists()) {
               return cls.create(ds);
            } else {
                Log.d("RESTEASY_FireStore_Load", "Doc Not Found:" + ds.toString());
            }
        } catch (IllegalAccessException ex) {
            //...
            Log.d("RESTEASY_Error", ex.toString());
        } catch (RuntimeExecutionException ex) {
            Log.d("RESTEASY_Error", ex.toString());
        }
        return cls.create();
    }
}

public abstract class FireStore {

    abstract <T> T create();

    <T> T create(DocumentSnapshot ds) throws IllegalAccessException, NoSuchFieldException {
        T cls = create();
        for (Field f : cls.getClass().getDeclaredFields()) {
            if (f.getName().equals("otherVals")) {
                HashMap<String, Object> values = (HashMap<String, Object>)f.get(cls);
                ArrayList<String> fields = (ArrayList<String>)
                        cls.getClass().getDeclaredField("otherFields").get(cls);
                for (String c : fields) {
                    values.put(c, ds.get(c));
                }
            }
            else if (!java.lang.reflect.Modifier.isStatic(f.getModifiers())
                    && ds.contains(f.getName())) {
                f.set(cls, ds.get(f.getName()));
                Log.d("RESTEASY_FireStore_Load",
                        ds.getReference().getPath() + "." +
                                f.getName() + ": " + ds.get(f.getName()));
            }
        }

        return cls;
    }

    static <T> Task<Void> save(DocumentReference document, T cls) {
        Map<String, Object> fields = new HashMap<String, Object>();
        try {
            for (Field f : cls.getClass().getDeclaredFields()) {
                if (!java.lang.reflect.Modifier.isStatic(f.getModifiers())) {
                    fields.put(f.getName(), f.get(cls));
                    Log.d("RESTEASY_FireStore_Save",
                            document.getPath() + "." + f.getName() + ": " + f.get(cls));
                }
            }
        } catch(IllegalAccessException ex) {
            //...
            Log.d("RESTEASY_Error",ex.toString());
        }

        if (!fields.isEmpty()) {
            Task<Void> task = document.set(fields);
            task.addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> t) {
                    if (!t.isSuccessful()) {
                        Log.d("RESTEASY_Error", t.getException().toString());
                    }
                }
            });
            return task;
        } else {
            return null;
        }
    }

    static <T extends FireStore> Task<T> load(DocumentReference document, T cls) {
        Task<DocumentSnapshot> task = document.get();
        return task.continueWith(new LoadContinuation<T>(cls));
    }

    static <T extends FireStore> Task<Map<String, T>> loadAll(CollectionReference col, T cls) {
        Task<QuerySnapshot> task = col.get();
        return task.continueWith(new LoadAllContinuation<T>(cls));
    }
}
