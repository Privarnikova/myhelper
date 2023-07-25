

package example.myapplication1.healthassistant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.Nullable;

import java.util.List;

import example.myapplication1.healthassistant.models.lekarstva;

public class LekAdapter extends ArrayAdapter<lekarstva>
{

    public LekAdapter(Context context, List<lekarstva> leks)
    {
        super(context, 0, leks);
    }

    @Override
    public View getView(int position, @Nullable View convertView, ViewGroup parent)
    {

        lekarstva lekarstva = getItem(position);
        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.lek_cel3, parent, false);


        TextView nazv = convertView.findViewById(R.id.nazv);
        String lekTitle = lekarstva.getName() ;
        nazv.setText(lekTitle);


        TextView doz = convertView.findViewById(R.id.data);
        TextView time = convertView.findViewById(R.id.time);
        TextView time2 = convertView.findViewById(R.id.time3);
        TextView time3 = convertView.findViewById(R.id.time4);

        LinearLayout l2 = convertView.findViewById(R.id.l2);
        LinearLayout l3 = convertView.findViewById(R.id.l3);


        TextView kol = convertView.findViewById(R.id.ml);

        if(lekarstva.getY()==0){
            time.setText(" ");
        }else {
            String lekT = lekarstva.getT1();
            time.setText(lekT);
        }
        String lekkol = lekarstva.getKol();
        if(lekarstva.getY()==0){
            kol.setText(" ");
        }else {
            kol.setText(lekkol + " в день");
        }

        String lekDoz = "по " + lekarstva.getDoz() + " таблетки";
        doz.setText(lekDoz);

        ImageView g1 = convertView.findViewById(R.id.imageButton2);
        g1.setBackgroundResource(R.drawable.ic_check);
        g1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (g1.getTag().equals("image1")) {
                        g1.setImageResource(R.drawable.ic_check_green);
                        g1.setTag("image2");
                    } else {
                        g1.setImageResource(R.drawable.ic_check);
                        g1.setTag("image1");
                    }
                }
            });
        ImageView g2 = convertView.findViewById(R.id.imageButton3);
        String lekT2 = lekarstva.getT2() ;
        switch (lekarstva.getKol()) {
            case "2 раза":

                l2.setVisibility(convertView.VISIBLE);
                time2.setText(lekT2);
                g2.setBackgroundResource(R.drawable.ic_check);
                g2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        g2.setBackgroundResource(R.drawable.ic_check_green);
                    }
                });
                break;
            case "3 раза":
                l2.setVisibility(convertView.VISIBLE);
                l3.setVisibility(convertView.VISIBLE);
                ImageView g3 = convertView.findViewById(R.id.imageButton4);
                time2.setText(lekT2);
                g2.setBackgroundResource(R.drawable.ic_check);
                g2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        g2.setBackgroundResource(R.drawable.ic_check_green);
                    }
                });
                String lekT3 = lekarstva.getT3() ;
                time3.setText(lekT3);

                g3.setBackgroundResource(R.drawable.ic_check);
                g3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        g3.setBackgroundResource(R.drawable.ic_check_green);
                    }
                });
                break;
            default:

                break;
        }




        return convertView;
    }


}