package xyz.leapmind.ceb.campus_e_board.Main;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import xyz.leapmind.ceb.campus_e_board.Main.Fragments.Group;
import xyz.leapmind.ceb.campus_e_board.Main.Fragments.Projects;
import xyz.leapmind.ceb.campus_e_board.Main.Fragments.PublicNotices;
import xyz.leapmind.ceb.campus_e_board.Main.Fragments.Recent;
import xyz.leapmind.ceb.campus_e_board.Main.Fragments.StudentClass;

public class Adapter extends FragmentPagerAdapter {
    public boolean student;

    public Adapter(FragmentManager fm) {
        super(fm);
    }

    public Fragment getItem(int pos) {

        Fragment frag = null;
        if (student) {
            if (pos == 0)
                frag = new PublicNotices();
            else if (pos == 1)
                frag = new Projects();
            else if (pos == 2)
                frag = new StudentClass();
            else if (pos == 3)
                frag = new Group();
        } else {
            if (pos == 0)
                frag = new PublicNotices();
            else if (pos == 1)
                frag = new Recent();
        }
        return frag;
    }

    public int getCount() {
        if (student)
            return 4;
        else
            return 2;
    }

}
