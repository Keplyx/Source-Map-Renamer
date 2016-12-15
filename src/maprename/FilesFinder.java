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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;
import maprename.gui.MapRenameUI;

/**
 *
 * @author keplyx
 */
public class FilesFinder {

    private static String csgoDir = null;

    private static final String MAPSPATH = "/maps/";
    private static final String CFGPATH = "/maps/cfg/";
    private static final String MINIMAP = "/resource/overviews/";
    private static final String SOUNDSCAPE = "/scripts/";

    public boolean findAll(MapRenameUI ui, String mapName) {
        csgoDir = ui.getCsgoDir();
        ui.writeToConsole("Finding files...\n");

        return find(ui, mapName, MAPSPATH, ".bsp", ".nav", ".kv", ".txt")
                && find(ui, mapName, CFGPATH, ".cfg") && find(ui, mapName, MINIMAP, ".txt", ".dds")
                && find(ui, mapName, SOUNDSCAPE, ".txt");

    }

    private boolean find(MapRenameUI ui, String name, String folder, String... extensions) {
        Stream<Path> paths;
        try {
            // Récupère tous les fichiers correspondant.
            paths = Files.list(Paths.get(csgoDir + folder)).filter(p -> {
                String n = p.getFileName() + "";
                String e = null;
                boolean ok = false;

                for (String ext : extensions) {
                    if (n.endsWith(ext)) {
                        e = ext;
                        ok = true;
                        break;
                    }
                }
                return ok && (n.equals(name + e) || n.matches("^" + name + "(_higher|_lower)?_radar(_spectate)?\\.dds$")
                        || n.matches("^" + name + "(_camera|_level_sounds).txt$")
                        || n.matches("^soundscapes_" + name + ".txt$"));
            });
        } catch (IOException ex) {
            return false;
        }

        if (paths != null) {
            for (Path path : paths.toArray(size -> new Path[size])) {
                ui.writeToConsole("Found " + folder + path.getFileName() + "\n");
                ui.addFilesToList(folder + path.getFileName());
            }
        }
        return true;
    }
}
