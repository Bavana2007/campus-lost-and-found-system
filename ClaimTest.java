package campuslostandfound;

import java.util.ArrayList;
import java.util.List;

/**
 * Console test for the claim module (no Swing). Run it and take screenshots
 * of the output for the report.
 * Part 1 needs no database. Part 2 needs MySQL: change the ids to real rows.
 */
public class ClaimTest {

    public static void main(String[] args) {
        System.out.println("=== Part 1: OOP demo (no database) ===");

        // Polymorphism: one list, two subclasses, different behaviour
        List<Claim> claims = new ArrayList<>();
        claims.add(new LostItemClaim(1, 101, "Saw it near the library entrance"));
        claims.add(new FoundItemClaim(2, 102, "Black wallet with my college ID inside"));
        for (Claim c : claims) {
            System.out.println(c.getClaimSummary()
                    + " | min message length = " + c.getMinMessageLength());
        }

        // Encapsulation + custom exception
        try {
            new Claim(1, 101, "   ");
        } catch (ClaimException e) {
            System.out.println("Blocked invalid claim: " + e.getMessage());
        }

        // Enum
        System.out.println("Default status: " + claims.get(0).getStatus());

        System.out.println();
        System.out.println("=== Part 2: Service + MySQL ===");
        ClaimService service = new ClaimService();
        int itemId = 1;       // an existing item_id
        int claimantId = 1;   // an existing user_id

        System.out.println("Empty message  : " + service.submitClaim(itemId, claimantId, ""));
        System.out.println("Short message  : " + service.submitClaim(itemId, claimantId, "mine"));
        System.out.println("Valid claim    : " + service.submitClaim(itemId, claimantId,
                "Blue backpack with a red keychain and my name inside"));
        System.out.println("Duplicate claim: " + service.submitClaim(itemId, claimantId,
                "Blue backpack with a red keychain and my name inside"));

        System.out.println("My claims:");
        for (Claim c : service.viewMyClaims(claimantId)) System.out.println("  " + c);

        System.out.println("Pending claims:");
        List<Claim> pending = service.getPendingClaims();
        for (Claim c : pending) System.out.println("  " + c);

        if (!pending.isEmpty()) {
            int id = pending.get(0).getClaimId();
            System.out.println("Approve        : " + service.approveClaim(id));
            System.out.println("Approve again  : " + service.approveClaim(id));
        }
    }
}
