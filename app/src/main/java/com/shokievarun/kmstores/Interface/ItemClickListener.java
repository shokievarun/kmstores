package com.shokievarun.kmstores.Interface;

import android.view.View;

public interface ItemClickListener
   {
         void onClick(View view,int position,boolean isLongClick);

       void onItemClick(View view, int adapterPosition);
   }
