/*
 * Copyright (C) 2016 keplyx
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * contact: keplyx@gmail.com
 */
package maprename;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import maprename.gui.MapRenameUI;

/**
 *
 * @author keplyx
 */
public class MapRenamer {

    private String newName = null;
    private String oldName = null;
    private final String[] filesPaths;

    private final File[] newfilesList;
    private final File[] oldfilesList;
    private String[] existing;

    MapRenameUI ui;

    public MapRenamer(MapRenameUI renameUI, String newN, String oldN, String... paths) {
        newName = newN;
        oldName = oldN;
        filesPaths = paths;
        oldfilesList = new File[filesPaths.length];
        newfilesList = new File[filesPaths.length];
        ui = renameUI;
    }

    private boolean renameCopy() {
        try {
            for (int i = 0; i < oldfilesList.length; i++) {
                ui.writeToConsole("Renaming: " + oldfilesList[i].getName() 
                        + " -> " + newfilesList[i].getName());
                byte[] bytes = Files.readAllBytes(Paths.get(oldfilesList[i].getPath()));
                Files.write(Paths.get(newfilesList[i].getPath()), bytes);
                ui.writeToConsole(" | SUCCESS\n");
                renameContent(newfilesList[i]);
            }
        } catch (IOException ex) {
            ui.writeToConsole("Could not copy: " + ex.getMessage() + "\n");
            return false;
        }
        return true;
    }

    private boolean renameOriginal() {
        for (int i = 0; i < oldfilesList.length; i++) {
            if (oldfilesList[i].renameTo(newfilesList[i])) {
                ui.writeToConsole("Renamed: " + oldfilesList[i].getName()
                        + " -> " + newfilesList[i].getName() + " | SUCCESS\n");

                renameContent(newfilesList[i]);
            } else {
                ui.writeToConsole("Could not rename: " + oldfilesList[i].getName()
                        + " -> " + newfilesList[i].getName() + " | FAILED\n");
                return false;
            }
        }
        return true;
    }

    private void renameContent(File file) {
        if (file.getName().endsWith(".kv") || file.getName().endsWith(".txt")) {
            ui.writeToConsole(" -> Renaming content: " + file.getName());
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                List<String> lines = new ArrayList<>();
                String line;

                while ((line = br.readLine()) != null) {

                    lines.add(line.replace(oldName, newName));
                }
                br.close();

                PrintWriter pw = new PrintWriter(file);
                lines.forEach((s) -> {
                    pw.println(s);
                });
                pw.close();
                ui.writeToConsole(" | SUCCESS\n");
            } catch (IOException ex) {
                ui.writeToConsole("\nCould not open file: " + file.getName() + " | FAILED\n");
            }
        }

    }

    public boolean selectRenameType(boolean isOverwrite) {
        if (isOverwrite) {
            ui.writeToConsole("\n------ Renaming original files ------\n\n");
            return renameOriginal();
        } else {
            ui.writeToConsole("\n------ Renaming copies ------\n\n");
            return renameCopy();
        }
    }

    public boolean checkExisting() {
        existing = new String[newfilesList.length];
        boolean alreadyExists = false;

        for (int i = 0; i < newfilesList.length; i++) {
            if (newfilesList[i].exists()) {
                existing[i] = newfilesList[i].getPath();
                alreadyExists = true;
            }
        }
        return alreadyExists;
    }

    public String[] getExistingFiles() {
        return existing;
    }

    public boolean getFiles() {
        for (int i = 0; i < filesPaths.length; i++) {
            oldfilesList[i] = new File(filesPaths[i]);

            int pos = filesPaths[i].lastIndexOf(oldName);

            if (pos >= 0) {
                newfilesList[i] = new File(filesPaths[i].substring(0, pos)
                        + newName + filesPaths[i].substring(pos + oldName.length(), filesPaths[i].length()));
            } else {
                return false;
            }
        }
        return true;
    }
}
