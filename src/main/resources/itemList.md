Here are 25 sets and 15 standalone items, with contents and sell values. Values are *suggestions* — you'll tune them once you see what a heist actually nets.

Two conventions I used, worth knowing:
- **Piece value** = what one item sells for alone.
- **Set value** = what the complete set sells for (always above the sum of piece values — that's the incentive to complete).

### 25 Sets

| #     | Set name | Contents | Piece value (each) | Set value |
|-------|---|---|---|---|
| **1** | Candelabra Pair | Candelabra A, Candelabra B | 10 | 50 |
| 2     | Van Gogh Study | Sunflowers Study, Irises Study, Wheatfield Study | 40 | 200 |
| 3     | Roman Hoard | Silver Denarius, Gold Aureus, Bronze Sestertius | 15 | 70 |
| **4** | Royal Regalia | Crown, Scepter, Orb | 60 | 300 |
| 5     | Armor Set | Cuirass, Greaves, Helmet | 25 | 120 |
| 6     | Tea Service | Teapot, Creamer, Sugar Bowl | 12 | 55 |
| 7     | Jade Collection | Jade Bi Disc, Jade Pendant, Jade Bowl | 30 | 150 |
| 8     | Illuminated Pages | Vellum Page I, Vellum Page II, Vellum Page III | 20 | 95 |
| 9     | Astrolabe Set | Astrolabe, Compass, Sundial | 18 | 80 |
| 10    | Fabergé Eggs | Egg I, Egg II, Egg III | 50 | 250 |
| 11    | Bronze Busts | Bust of Senator, Bust of Emperor | 35 | 100 |
| 12    | Silverware | Serving Fork, Serving Spoon, Ladle | 8 | 35 |
| **13** | Antique Maps | Map of the New World, Map of the Old World | 22 | 70 |
| **14** | Samurai Kit | Katana, Wakizashi, Tanto | 45 | 220 |
| 15    | Egyptian Tomb | Scarab Amulet, Canopic Jar, Funerary Mask | 55 | 280 |
| 16    | Renaissance Sketches | Da Vinci Sketch, Michelangelo Sketch, Raphael Sketch | 48 | 240 |
| 17    | Chess Set | White King, Black King, Board | 30 | 140 |
| 18    | Russian Icons | Icon of Saint I, Icon of Saint II | 28 | 90 |
| 19    | Pearl Necklace | Pearl Strand, Pearl Earrings, Pearl Brooch | 16 | 70 |
| 20    | Tribal Masks | Mask of the Sun, Mask of the Moon, Mask of the Rain | 24 | 115 |
| 21    | Alchemical Kit | Alembic, Mortar and Pestle, Grimoire | 32 | 150 |
| 22    | Crown Jewels | Ruby Ring, Sapphire Ring, Emerald Ring | 40 | 180 |
| 23    | Viking Hoard | Axe Head, Silver Torc, Rune Stone | 26 | 125 |
| 24    | Porcelain Figures | Dancer, Musician, Poet | 14 | 65 |
| 25    | Astronomical Clock | Clock Face, Clock Movement, Pendulum | 38 | 175 |

### 15 Standalone Items

| #      | Item name | Room flavor | Piece value |
|--------|---|---|---|
| 1      | The Restoration Painting | RESTORATION (mid-restoration) | 60 |
| **2**  | Office Cash Box | SECURITY_OFFICE | 25 |
| 3      | Deposit Slips | STORAGE | 5 |
| 4      | Brass Fittings | STORAGE | 6 |
| 5      | Packing Crate | STORAGE | 4 |
| 6      | Marble Fragment | STORAGE | 8 |
| 7      | Velvet Rope | LOBBY | 3 |
| 8      | Visitor Register | LOBBY | 2 |
| **9**  | Donation Box | LOBBY | 12 |
| 10     | Gallery Placard | EAST_GALLERY | 2 |
| 11     | Display Case Lock | WEST_GALLERY | 5 |
| 12     | Postcard Rack | LOBBY | 3 |
| 13     | Museum Keycard | SECURITY_OFFICE | 15 |
| 14     | Tool Cabinet | RESTORATION | 18 |
| **15** | Main Score (safe_contents) | VAULT | — (flag) |
| **16** | Wooden Pallet | STORAGE | 3 | 1.0 |
| 17     | Canvas Roll | STORAGE | 7 | 1.0 |
| 18     | Burlap Sack | STORAGE | 4 | 1.0 |
| **19** | Iron Key Ring | STORAGE | 9 | 1.0 |
| 20     | Empty Frame | STORAGE | 6 | 1.0 |
| 21     | Cardboard Tube | STORAGE | 2 | 1.0 |
| 22     | Loose Marbles | STORAGE | 3 | 1.0 |
| 23     | Wooden Crate | STORAGE | 5 | 1.0 |
| 24     | Packing Foam | STORAGE | 1 | 1.0 |
| 25     | Old Ledger | STORAGE | 11 | 1.0 |
| **26** | Linen Wrapping | STORAGE | 2 | 1.0 |
| **27** | Brass Lamp | STORAGE | 14 | 1.0 |
| 28     | Stone Bust Fragment | STORAGE | 16 | 1.0 |
| 29     | Tarnished Mirror | STORAGE | 13 | 1.0 |
| 30     | Ceramic Vase | STORAGE | 10 | 1.0 |
| 31     | Coins Jar | STORAGE | 8 | 1.0 |
| 32     | Rusted Tools | STORAGE | 7 | 1.0 |
| 33     | Velvet Cushion | LOBBY | 5 | 1.0 |
| 34     | Framed Photo | LOBBY | 4 | 1.0 |
| 35     | Desk Ornament | SECURITY_OFFICE | 12 | 1.0 |

### A few notes

- **The set values scale.** Cheap sets (silverware, 35) sit alongside expensive ones (Royal Regalia, 300). That gives the museum's loot variety without every item feeling the same.
- **The standalone items are mostly low-value junk** — that's deliberate. "Bulk loot" in STORAGE should be *many small grabs* that add up, not a single high-value item. The temptation is that you could carry out five crates' worth if you had time.
- **The Restoration Painting is the stand-out** (60) — the off-route high-value item. Worth a detour, but it's the riskiest grab in the museum.
- **The Main Score** shouldn't be in the standalone list as a normal item — it's the safe contents, and it's already a special case.
