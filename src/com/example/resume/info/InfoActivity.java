package com.example.resume.info;

import java.io.File;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.resume.ActivityUtils;
import com.example.resume.MainActivity;
import com.example.resume.R;
import com.example.resume.prefrence.UserInfoPrefrence;
import com.example.resume.utils.BitMapUtils;
import com.example.resume.utils.MyDate;
import com.example.resume.utils.Tools;


public class InfoActivity extends Activity {

	public final static String EXTRA_NAME = "com.example.resume.NAME";
	public final static String EXTRA_GENDER = "com.example.resume.GENDER";
	public final static String EXTRA_EMAIL = "com.example.resume.EMAIL";
	private String[] items = new String[] { "ѡ�񱾵�ͼƬ", "����" };
	private int outputx = 320;//�ü��Ŀ��
	private int outputy = 320;//�ü��ĸ߶�
	/* ͷ������ */
	private static final String IMAGE_FILE_NAME = "faceImage.jpg";
	/* ������ */
	private static final int IMAGE_REQUEST_CODE = 0;
	private static final int CAMERA_REQUEST_CODE = 1;
	private static final int RESULT_REQUEST_CODE = 2;
	
	public static String avatar_dir = null;
	public static String avatar_name = "resume_avatar.jpg";
	public static String avatar_path = null;
    String birthday = null;
    
    TableRow avatarRow = null;
	TableRow nameRow = null;
	TableRow genderRow = null;
	TableRow dateRow = null;
	TableRow mobileRow = null;
	TableRow emailRow = null;
	TableRow addressRow = null;
	
	ImageView avatarImage = null;
	TextView nameText = null;
	TextView genderText = null;
	TextView dateText = null;
	TextView mobileText = null;
	TextView emailText = null;
	TextView addressText = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		initData();
		prepareElements();
		setStatus();
		addListener();
		
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	

	
	private void initData() {
		avatar_dir = Environment.getExternalStorageDirectory().getPath()+ this.getFilesDir().getPath() + File.separatorChar;
		avatar_path = avatar_dir + avatar_name;
		Log.d("resume", avatar_dir);
		Log.d("resume", avatar_path);
	}
	
	
  /**
   * ��Ԫ�ؽ��г�ʼ������
   */
	private void prepareElements()
	{
		//ͷ��
		avatarRow = (TableRow) findViewById(R.id.avatar);
		avatarImage = (ImageView) findViewById(R.id.myavatar);
		//����
		nameRow = (TableRow) findViewById(R.id.name);
		nameText = (TextView) findViewById(R.id.myname);
		//�Ա�
		genderRow = (TableRow) findViewById(R.id.gender);
		genderText = (TextView) findViewById(R.id.mygender);
		//��������
		dateRow = (TableRow) findViewById(R.id.birthday);
		dateText = (TextView) findViewById(R.id.mybirthday);
		//�ֻ�����
		mobileRow = (TableRow) findViewById(R.id.mobile);
		mobileText = (TextView) findViewById(R.id.mymobile);
		//����
		emailRow = (TableRow) findViewById(R.id.email);
		emailText = (TextView) findViewById(R.id.myemail);
		//��ַ
		addressRow = (TableRow) findViewById(R.id.address);
		addressText = (TextView) findViewById(R.id.myaddress);
	}
	
	private void setStatus()
	{
		Bitmap bm = BitMapUtils.getAvatarBitmap(avatar_path);
		if(bm == null)
		{
			Drawable avatar = this.getResources().getDrawable(R.drawable.avatar);
			avatarImage.setImageDrawable(avatar);
			
		}else
		{
			 avatarImage.setImageBitmap(bm);
		}
       
        
		String name = UserInfoPrefrence.getNamePrefence();
		nameText.setText(name);
		
		String gender = UserInfoPrefrence.getGenderPrefence();
		genderText.setText(gender);
		
		birthday = UserInfoPrefrence.getBirthdayPrefence();
		dateText.setText(birthday);
		
		String mobile = UserInfoPrefrence.getMobilePrefence();
		mobileText.setText(mobile);
		
		String email = UserInfoPrefrence.getEmailPrefence();
		emailText.setText(email);
		
		String address = UserInfoPrefrence.getAddressPrefence();
		addressText.setText(address);
		
		
	}
	
	private void addListener()
	{
		avatarRow.setOnClickListener(new clickAvatarRowAction());
		avatarImage.setOnClickListener(new clickToShowAvatarction());
		nameRow.setOnClickListener(new clickNameRowAction());
		genderRow.setOnClickListener(new clickGenderRowAction());
		dateRow.setOnClickListener(new clickToShowDateAction());
		mobileRow.setOnClickListener(new clickMobileRowAction());
		emailRow.setOnClickListener(new clickEmailRowAction());
		addressRow.setOnClickListener(new clickAddressRowAction());
	}
	 
	
	
	
	 @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // TODO Auto-generated method stub
        if(item.getItemId() == android.R.id.home)
        {
        	ActivityUtils.gotoActivity(this, MainActivity.class);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
	
	@Override 
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	  if(keyCode == KeyEvent.KEYCODE_BACK){ 
		  ActivityUtils.gotoActivity(this, MainActivity.class);
		  return true;
	  }
	  return super.onKeyDown(keyCode, event);
	}
	

	/**
	 * ͷ��Ի���
	 */
	private void showChooseAvatarDialog() 
	{
		Builder build=new AlertDialog.Builder(this);
		build.setItems(items, new clickToShowAvatarDialogAction());
		build.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		build.show();
	}
	
	
	private void showAvatarDialog() 
	{
		Dialog dialog = new AvatarDialog(this,R.style.myDialogTheme);
		dialog.setCancelable(true);
//		dialog.set
        dialog.show();
	}
	
	
	/**
	 * ���ڶԻ���
	 */
	private void showDateDialog() 
	{


		String birthday = dateText.getText().toString();
		MyDate d = new MyDate(birthday);
	    Dialog dialog = new DatePickerDialog(this, new setBirthdayAction(), 
	    		d.getIntegerYear(), d.getIntegerMonth()-1, d.getIntegerDay());  
        dialog.show();
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		//����벻����ȡ��ʱ��
		if (resultCode != RESULT_CANCELED) {

			switch (requestCode) {
			case IMAGE_REQUEST_CODE:
				startPhotoZoom(data.getData());
				break;
			case CAMERA_REQUEST_CODE:
				if (Tools.hasSdcard()) {
					File tempFile = new File(Environment.getExternalStorageDirectory()+ IMAGE_FILE_NAME);
					startPhotoZoom(Uri.fromFile(tempFile));
				} else {
					ActivityUtils.showTip(this, "δ�ҵ��洢�����޷��洢��Ƭ");
				}

				break;
			case RESULT_REQUEST_CODE:
				if (data != null) {
					getImageToView(data);
				}
				break;
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * �ü�ͼƬ����ʵ��
	 * 
	 * @param uri
	 */
	public void startPhotoZoom(Uri uri) {

		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// ���òü�
		intent.putExtra("crop", "true");
		// aspectX aspectY �ǿ�ߵı���
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY �ǲü�ͼƬ���
//		Display mDisplay = getWindowManager().getDefaultDisplay();
//		int outputx = mDisplay.getWidth();
//		int outputy = mDisplay.getHeight();
		intent.putExtra("outputX", outputx);
		intent.putExtra("outputY", outputy);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, 2);
	}

	/**
	 * ����ü�֮���ͼƬ����
	 * 
	 * @param picdata
	 */
	private void getImageToView(Intent data) {
		Bundle extras = data.getExtras();
		if (extras != null) {
			Bitmap photo = extras.getParcelable("data");
			Drawable drawable = new BitmapDrawable(photo);
			avatarImage.setImageDrawable(drawable);
			BitMapUtils.storeImageToSDCARD(photo, avatar_name, avatar_dir);
		}
	}
	
		
	 private class clickAvatarRowAction implements OnClickListener
	 {

			public void onClick(View v) {

				showChooseAvatarDialog();
			}
	    	
	 }
	 
	 private class clickToShowAvatarction implements OnClickListener
	 {

			public void onClick(View v) {
				showAvatarDialog() ;
				//ActivityUtils.gotoActivity(InfoActivity.this, ShowAvatarActivity.class);
			}
	    	
	 }
	 
		private class clickToShowAvatarDialogAction implements DialogInterface.OnClickListener
	    {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case 0:
					Intent intentFromGallery = new Intent();
					intentFromGallery.setType("image/*"); // �����ļ�����
					intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
					startActivityForResult(intentFromGallery,IMAGE_REQUEST_CODE);
					break;
				case 1:

					Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					// �жϴ洢���Ƿ�����ã����ý��д洢
					if (Tools.hasSdcard()) {

						intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT,
								Uri.fromFile(new File(Environment.getExternalStorageDirectory(),IMAGE_FILE_NAME)));
					}

					startActivityForResult(intentFromCapture,CAMERA_REQUEST_CODE);
					break;
				}
			}
		}
	 
	 
	 private class clickToShowDateAction implements OnClickListener
	 {
			public void onClick(View v) {
				showDateDialog();
			}
	    	
	  }
	 
    
	private class clickNameRowAction implements OnClickListener
    {

		public void onClick(View v) {

			Intent in = new Intent(InfoActivity.this, ModifyNameActivity.class); 
	    	String name = nameText.getText().toString();
	    	in.putExtra(EXTRA_NAME, name);
        	startActivityForResult(in, 0);
		}
    	
    }
	
	private class clickGenderRowAction implements OnClickListener
    {

		public void onClick(View v) {

			Intent in = new Intent(InfoActivity.this, ModifyGenderActivity.class); 

	    	String gender = genderText.getText().toString();
	    	in.putExtra(EXTRA_GENDER, gender);
        	startActivityForResult(in, 0);
		}
    	
    }
	

	
	private class setBirthdayAction implements OnDateSetListener
	{

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {
			String birthday = year + "-" + (monthOfYear+1) + "-" + dayOfMonth;
            dateText.setText(birthday);
            UserInfoPrefrence.setBirthdayPrefence(birthday);
            
		}
		
	}
	
	private class clickMobileRowAction implements OnClickListener
    {

		public void onClick(View v) {

			Intent in = new Intent(InfoActivity.this, ModifyMobileActivity.class); 
        	startActivityForResult(in, 0);
		}
    	
    }
	
	
	private class clickEmailRowAction implements OnClickListener
    {

		public void onClick(View v) {

			Intent in = new Intent(InfoActivity.this, ModifyEmaiActivity.class); 
	    	String email = emailText.getText().toString();
	    	in.putExtra(EXTRA_EMAIL, email);
        	startActivityForResult(in, 0);
		}
    	
    }
	
	private class clickAddressRowAction implements OnClickListener
    {

		public void onClick(View v) {

			Intent in = new Intent(InfoActivity.this, ModifyAddressActivity.class); 
        	startActivityForResult(in, 0);
		}
    	
    }
	


}
