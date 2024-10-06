package com.example.foodplannerapplication.features.Scan.view;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.foodplannerapplication.R;
import com.example.foodplannerapplication.database.ConcreteLocalSource;
import com.example.foodplannerapplication.features.Scan.presenter.SettingsPresenter;
import com.example.foodplannerapplication.models.repository.Repo;
import com.example.foodplannerapplication.network.ApiClient;
import com.example.foodplannerapplication.util.NetworkConnection;

//import androidx.camera.core.CameraSelector;
//import androidx.camera.core.ImageAnalysis;
//import androidx.camera.core.ImageProxy;
//import androidx.camera.core.Preview;
//import androidx.camera.lifecycle.ProcessCameraProvider;
//import com.google.common.util.concurrent.ListenableFuture;
//import org.tensorflow.lite.Interpreter;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.nio.ByteBuffer;
//import java.nio.ByteOrder;
//import java.nio.MappedByteBuffer;
//import java.nio.channels.FileChannel;
//import java.util.concurrent.ExecutionException;
//import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
//import io.reactivex.rxjava3.core.Observable;
//import io.reactivex.rxjava3.disposables.CompositeDisposable;
//import io.reactivex.rxjava3.schedulers.Schedulers;




public class ScanFragment extends Fragment implements ScanView {
    public static final String TAG = "SettingsFragment";
    private Button btnforward, btnBackup;
    private SettingsPresenter presenter;

    private LottieAnimationView lvDone;
    private MediaPlayer mediaPlayer;

//    private ProcessCameraProvider cameraProvider;
//    private Interpreter tfliteInterpreter;
//    private CompositeDisposable disposable = new CompositeDisposable();
//    private TextView detectionResultTextView;
//private static final String MODEL_PATH = "yolov5s.tflite";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new SettingsPresenter(this,
                Repo.getInstance(
                        ApiClient.getInstance(),
                        ConcreteLocalSource.getInstance(getContext())
                ));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        startCamera(view);
//        loadModel();
        mediaPlayer = MediaPlayer.create(getContext(), R.raw.sscan);
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        //initClicks();

        presenter.isDoneWork();
    }

//    private void startCamera(View view) {
//        ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext());
//
//        cameraProviderFuture.addListener(() -> {
//            try {
//                cameraProvider = cameraProviderFuture.get();
//                bindCameraUseCases(view);
//            } catch (ExecutionException | InterruptedException e) {
//                // Handle camera provider initialization failure
//            }
//        }, ContextCompat.getMainExecutor(requireContext()));
//    }
//
//    private void bindCameraUseCases(View view) {
//        PreviewView previewView = view.findViewById(R.id.previewView);
//        Preview preview = new Preview.Builder().build();
//        CameraSelector cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA;
//
//        ImageAnalysis imageAnalysis = new ImageAnalysis.Builder()
//                .setTargetResolution(new Size(640, 640))
//                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
//                .build();
//
//        imageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(requireContext()), image -> {
//            if (tfliteInterpreter != null) {
//                // Process the image using RxJava
//                disposable.add(processImage(image)
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(
//                                result -> detectionResultTextView.setText(result),
//                                throwable -> detectionResultTextView.setText("Error: " + throwable.getMessage())
//                        ));
//            }
//            image.close();
//        });
//
//        preview.setSurfaceProvider(previewView.getSurfaceProvider());
//
//        cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageAnalysis);
//    }
//
//    private Observable<String> processImage(ImageProxy image) {
//        return Observable.create(emitter -> {
//            try {
//                ByteBuffer inputBuffer = preprocessImage(image);
//                float[][] outputBuffer = new float[1][85];  // Adjust according to YOLOv5 output size
//
//                // Run inference
//                tfliteInterpreter.run(inputBuffer, outputBuffer);
//
//                // Post-process the output and send the result to the observer
//                String detectionResult = postProcessOutput(outputBuffer);
//                emitter.onNext(detectionResult);
//                emitter.onComplete();
//            } catch (Exception e) {
//                emitter.onError(e);
//            }
//        });
//    }
//
//    private void loadModel() {
//        try {
//            AssetFileDescriptor fileDescriptor = requireContext().getAssets().openFd(MODEL_PATH);
//            FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
//            FileChannel fileChannel = inputStream.getChannel();
//            long startOffset = fileDescriptor.getStartOffset();
//            long declaredLength = fileDescriptor.getDeclaredLength();
//            MappedByteBuffer modelBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
//
//            tfliteInterpreter = new Interpreter(modelBuffer);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private ByteBuffer preprocessImage(ImageProxy image) {
//        // Convert ImageProxy to ByteBuffer, resize to 640x640, normalize pixel values
//        ImageProxy.PlaneProxy[] planes = image.getPlanes();
//        ByteBuffer inputBuffer = ByteBuffer.allocateDirect(640 * 640 * 3 * 4); // Assuming 3-channel RGB
//        inputBuffer.order(ByteOrder.nativeOrder());
//        // Fill inputBuffer with processed image data
//        return inputBuffer;
//    }
//
//    private String postProcessOutput(float[][] outputBuffer) {
//        // Interpret YOLOv5 model output: extract bounding boxes, class predictions, and confidence scores
//        StringBuilder result = new StringBuilder();
//        for (int i = 0; i < outputBuffer[0].length; i++) {
//            // Example output format: Adjust according to the actual output size of the YOLOv5 model
//            result.append("Object detected with confidence: ").append(outputBuffer[0][i]).append("\n");
//        }
//        return result.toString();
//    }

    @Override
    public void initViews(View view) {
        lvDone = view.findViewById(R.id.lv_done);
        btnforward = view.findViewById(R.id.btn_forward);
        btnBackup = view.findViewById(R.id.btn_backup);

    }


    private boolean isConnected() {
        if (NetworkConnection.isConnected(requireContext())) {
            return true;
        } else {
            Toast.makeText(getContext(), R.string.check_your_internet_connection_and_try_again, Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}